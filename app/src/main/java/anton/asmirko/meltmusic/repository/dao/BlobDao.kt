package anton.asmirko.meltmusic.repository.dao

import androidx.room.*
import anton.asmirko.meltmusic.model.data.Blob
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BlobDao {

    @Query("SELECT * FROM blob")
    fun getAll(): Single<List<Blob>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg blob: Blob): Completable

    @Delete
    fun deleteBlob(blob: Blob): Completable
}