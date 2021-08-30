package anton.asmirko.meltmusic.repository.dao

import androidx.room.*
import anton.asmirko.meltmusic.model.data.Metadata
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MetadataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(metadata: Metadata): Completable

    @Delete
    fun delete(metadata: Metadata): Completable

    @Query("SELECT * FROM metadata LIMIT :k")
    fun getKLatest(k: Int): Single<List<Metadata>>

    @Query("SELECT * FROM metadata")
    fun getAll(): Single<List<Metadata>>
}