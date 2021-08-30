package anton.asmirko.meltmusic.repository.dao

import androidx.room.*
import anton.asmirko.meltmusic.model.data.Transition

@Dao
interface TransitionDao {

    @Query("SELECT * FROM transition")
    fun getAll(): List<Transition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg transition: Transition)

    @Delete
    fun deleteBlob(transition: Transition)
}