package anton.asmirko.meltmusic.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blob")
data class Blob(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "song") val song: String,
    @ColumnInfo(name = "playlist") val playlist: String,
    @ColumnInfo(name = "start") val startMs: Long,
    @ColumnInfo(name = "end") val endMs: Long
)