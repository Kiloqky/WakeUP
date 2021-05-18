package ru.kiloqky.wakeup.rest.room.keep.dao


import androidx.room.*
import ru.kiloqky.wakeup.rest.room.keep.model.Keep

@Dao
interface KeepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeep(keep: Keep)

    @Delete
    suspend fun deleteKeep(keep: Keep)

    @Query("SELECT * FROM Keep")
    suspend fun getAll(): List<Keep>

    @Query("UPDATE Keep SET keep_title = :keepTitle, keep_body = :keepBody WHERE id = :id")
    suspend fun updateKeep(id: Long, keepTitle: String, keepBody: String)
}