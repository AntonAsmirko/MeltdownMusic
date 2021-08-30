package anton.asmirko.meltmusic.ui.activities

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import anton.asmirko.meltmusic.R
import anton.asmirko.meltmusic.application.MeltMusicApp
import anton.asmirko.meltmusic.databinding.ActivityMainBinding
import anton.asmirko.meltmusic.services.SpotifyBroadcastReceiver
import anton.asmirko.meltmusic.ui.fragments.TransitionsFragment
import anton.asmirko.meltmusic.utils.appComponent
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.PlayerApi
import com.spotify.android.appremote.api.SpotifyAppRemote
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var mSpotifyAppRemote: SpotifyAppRemote
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var connectionParams: ConnectionParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val root = binding.root
        setContentView(root)
        appComponent.inject(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, TransitionsFragment())
            .addToBackStack("kek")
            .commit()
    }

    override fun onStart() {
        super.onStart()
        SpotifyAppRemote.connect(this, connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    Log.d("MainActivity", "Connected! Yay!")
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("MainActivity", throwable.message, throwable)

                }
            })
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(SpotifyBroadcastReceiver(), IntentFilter().apply {
            addAction("com.spotify.music.playbackstatechanged")
            addAction("com.spotify.music.metadatachanged")
            addAction("com.spotify.music.queuechanged")
        })
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }
}