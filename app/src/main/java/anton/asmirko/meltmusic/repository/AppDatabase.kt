package anton.asmirko.meltmusic.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import anton.asmirko.meltmusic.model.data.Blob
import anton.asmirko.meltmusic.model.data.Metadata
import anton.asmirko.meltmusic.model.data.Transition
import anton.asmirko.meltmusic.repository.dao.BlobDao
import anton.asmirko.meltmusic.repository.dao.MetadataDao
import anton.asmirko.meltmusic.repository.dao.TransitionDao

@Database(
    entities = [
        Blob::class,
        Transition::class,
        Metadata::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun blobDao(): BlobDao
    abstract fun transitionDao(): TransitionDao
    abstract fun metadataDao(): MetadataDao
}