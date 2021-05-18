package ru.kiloqky.wakeup.rest.room.news

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kiloqky.wakeup.rest.room.news.dao.NewsDao
import ru.kiloqky.wakeup.rest.room.news.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}