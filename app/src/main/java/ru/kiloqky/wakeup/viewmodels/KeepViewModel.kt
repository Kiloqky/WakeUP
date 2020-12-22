package ru.kiloqky.wakeup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kiloqky.wakeup.rest.room.KeepDataBase
import ru.kiloqky.wakeup.rest.room.model.Keep
import kotlin.concurrent.thread


class KeepViewModel(var database: KeepDataBase) : ViewModel() {

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
        GlobalScope.launch {
            _recyclerKeeps.postValue(ArrayList(database.keepDao().getAll()))
        }
    }

}