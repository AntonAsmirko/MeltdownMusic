package anton.asmirko.meltmusic.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import anton.asmirko.meltmusic.model.data.Metadata
import anton.asmirko.meltmusic.application.MeltMusicApp
import anton.asmirko.meltmusic.model.data.Blob
import anton.asmirko.meltmusic.model.data.PlayBackState
import anton.asmirko.meltmusic.model.data.SpotifyBroadcastMessage
import anton.asmirko.meltmusic.repository.dao.BlobDao
import anton.asmirko.meltmusic.repository.dao.MetadataDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SpotifyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val SPOTIFY_PACKAGE = "com.spotify.music"
        const val PLAYBACK_STATE_CHANGED = "$SPOTIFY_PACKAGE.playbackstatechanged"
        const val QUEUE_CHANGED = "$SPOTIFY_PACKAGE.queuechanged"
        const val METADATA_CHANGED = "$SPOTIFY_PACKAGE.metadatachanged"
    }

    @Inject
    lateinit var metadataDao: MetadataDao

    private var spotifyBroadcastEntry: SpotifyBroadcastEntry? = null

    private fun submitMessage(spotifyBroadcastMessage: SpotifyBroadcastMessage) {
        if (spotifyBroadcastEntry == null || !spotifyBroadcastEntry!!.isAlive) {
            spotifyBroadcastEntry = SpotifyBroadcastEntry(spotifyBroadcastMessage).apply {
                MeltMusicApp.INSTANCE.appComponent.inject(this)
            }
        } else {
            spotifyBroadcastEntry?.submitMessage(spotifyBroadcastMessage)
        }
    }

    private fun persistNewMetadata(metadata: Metadata) {
        submitMessage(metadata)
    }

    private fun persistPlaybackState(playBackState: PlayBackState) {
        submitMessage(playBackState)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        MeltMusicApp.INSTANCE.appComponent.inject(this)
        intent?.action?.let {
            when (it) {
                METADATA_CHANGED -> intent.apply {
                    persistNewMetadata(
                        Metadata(
                            getStringExtra("id")!!,
                            getStringExtra("artist")!!,
                            getStringExtra("album")!!,
                            getStringExtra("track")!!,
                            getIntExtra("length", 0),
                            getLongExtra("broadcasts", 0)
                        )
                    )
                }
                PLAYBACK_STATE_CHANGED -> intent.apply {
                    persistPlaybackState(
                        PlayBackState(
                            getBooleanExtra("playing", false),
                            getIntExtra("playbackPosition", 0)
                        )
                    )
                }
                else -> {
                }
            }
        }
    }

    class SpotifyBroadcastEntry(
        spotifyBroadcastMessage: SpotifyBroadcastMessage
    ) {

        @Inject
        lateinit var blobDao: BlobDao

        private lateinit var metadata: Metadata
        private lateinit var playBackState: PlayBackState
        var isAlive = true

        init {
            set(spotifyBroadcastMessage)
        }

        fun submitMessage(message: SpotifyBroadcastMessage) {
            if(set(message)) {
                val disposable = blobDao.insertAll(
                    Blob(
                        song = metadata.id,
                        playlist = metadata.album,
                        startMs = playBackState.playbackPosition.toLong(),
                        endMs = metadata.length.toLong()
                    )
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                isAlive = false
            }
        }

        private fun set(spotifyBroadcastMessage: SpotifyBroadcastMessage): Boolean {
            return when (spotifyBroadcastMessage) {
                is Metadata
                -> if (!::metadata.isInitialized) {
                    metadata = spotifyBroadcastMessage
                    true
                } else {
                    false
                }
                else -> if (!::playBackState.isInitialized) {
                    playBackState = spotifyBroadcastMessage as PlayBackState
                    true
                } else {
                    false
                }
            }
        }
    }
}