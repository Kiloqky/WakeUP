package ru.kiloqky.wakeup.rest.room.news.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kiloqky.wakeup.rest.room.news.model.News

@Dao
interface NewsDao {

    @Query("DELETE FROM news_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(list: List<News>)

    @Query("SELECT * FROM news_table")
    suspend fun getAll(): List<News>
}