package anton.asmirko.meltmusic.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

interface SpotifyBroadcastMessage

data class PlayBackState(
    val playing: Boolean,
    val playbackPosition: Int
): SpotifyBroadcastMessage

@Entity(tableName = "metadata")
data class Metadata(
    @PrimaryKey val id: String,
    val artist: String,
    val album: String,
    val track: String,
    val length: Int,
    val timeSent: Long
): SpotifyBroadcastMessage