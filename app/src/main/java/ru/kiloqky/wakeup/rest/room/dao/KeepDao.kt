package ru.kiloqky.wakeup.rest.room.dao


import androidx.room.*
import ru.kiloqky.wakeup.rest.room.model.Keep

@Dao
interface KeepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeep(keep: Keep)

    @Delete
    fun deleteKeep(keep: Keep)

    @Query("SELECT * FROM Keep")
    fun getAll(): List<Keep>

    @Query("UPDATE Keep SET keep_title = :keepTitle, keep_body = :keepBody WHERE id = :id")
    fun updateKeep(id: Long, keepTitle: String, keepBody: String)
}