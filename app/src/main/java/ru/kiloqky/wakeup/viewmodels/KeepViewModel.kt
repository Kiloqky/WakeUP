package ru.kiloqky.wakeup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kiloqky.wakeup.rest.room.KeepDataBase
import ru.kiloqky.wakeup.rest.room.model.Keep


class KeepViewModel(private var database: KeepDataBase) : ViewModel() {

    //общение между фрагментами
    private val _editingKeep = MutableLiveData<Keep>()
    val editingKeep: LiveData<Keep> = _editingKeep

    fun editingKeep(keep: Keep) {
        GlobalScope.launch {
            _editingKeep.postValue(keep)
        }
    }

    //recycler Data
    private val _recyclerKeeps = MutableLiveData<List<Keep>>()
    val recyclerKeeps: LiveData<List<Keep>> = _recyclerKeeps

    //добавление новой заметки
    fun addKeep(keep: Keep) {
        GlobalScope.launch {
            database.keepDao().insertKeep(keep)
            fetchData()
        }
    }

    //удаление заметки
    fun deleteKeep(keep: Keep) {
        GlobalScope.launch {
            database.keepDao().deleteKeep(keep)
            fetchData()
        }
    }

    //обновление заметки
    fun editKeep(keep: Keep) {
        GlobalScope.launch {
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