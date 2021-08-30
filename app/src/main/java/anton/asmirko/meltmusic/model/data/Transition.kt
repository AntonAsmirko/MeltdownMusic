package anton.asmirko.meltmusic.model.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import anton.asmirko.meltmusic.model.data.Blob

@Entity(tableName = "transition")
data class Transition(
    @PrimaryKey val id: Int? = null,
    @Embedded(prefix = "start_") val start: Blob,
    @Embedded(prefix = "end_") val end: Blob
)