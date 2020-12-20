package ru.kiloqky.wakeup.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import ru.kiloqky.wakeup.rest.room.KeepDataBase
import ru.kiloqky.wakeup.rest.room.model.Keep
import kotlin.concurrent.thread


class KeepViewModel(application: Application) : AndroidViewModel(application) {

    //локальная бд
    private var database: KeepDataBase = Room.databaseBuilder(
        application.applicationContext,
        KeepDataBase::class.java,
        "keeps"
    ).build()

    //общение между фрагментами
    private val _editingKeep = MutableLiveData<Keep>().apply {}
    val editingKeep: LiveData<Keep> = _editingKeep
    fun editingKeep(keep: Keep) {
        thread {
            _editingKeep.postValue(keep)
        }
    }

    //recycler Data
    private val _recyclerKeeps =
        MutableLiveData<List<Keep>>().apply {}
    val recyclerKeeps: LiveData<List<Keep>> = _recyclerKeeps

    //добавление новой заметки
    fun addKeep(keep: Keep) {
        thread {
            database.keepDao().insertKeep(keep)
            fetchData()
        }
    }


    //удаление заметки
    fun deleteKeep(keep: Keep) {
        thread {
            database.keepDao().deleteKeep(keep)
            fetchData()
        }
    }

    //обновление заметки
    fun editKeep(keep: Keep) {
        thread {
            database.keepDao().updateKeep(keep.id, keep.keepTitle, keep.keepBody!!)
            fetchData()
        }
    }

    //обновление базы данных
    fun fetchData() {
        thread {
            _recyclerKeeps.postValue(ArrayList(database.keepDao().getAll()))
        }
    }

}