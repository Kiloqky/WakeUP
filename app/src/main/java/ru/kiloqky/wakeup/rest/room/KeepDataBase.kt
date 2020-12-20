package ru.kiloqky.wakeup.rest.room


import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kiloqky.wakeup.rest.room.dao.KeepDao
import ru.kiloqky.wakeup.rest.room.model.Keep


@Database(entities = [Keep::class], version = 1, exportSchema = false)
abstract class KeepDataBase : RoomDatabase() {
    abstract fun keepDao(): KeepDao
}