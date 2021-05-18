package ru.kiloqky.wakeup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import ru.kiloqky.wakeup.rest.room.keep.KeepDataBase
import ru.kiloqky.wakeup.rest.room.keep.model.Keep


class KeepViewModel(private var database: KeepDataBase) : ViewModel() {

    //общение между фрагментами
    private val _editingKeep = MutableSharedFlow<Keep>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val editingKeep: SharedFlow<Keep> = _editingKeep.asSharedFlow()

    private val _recyclerKeeps =
        MutableSharedFlow<List<Keep>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val recyclerKeeps: SharedFlow<List<Keep>> = _recyclerKeeps.asSharedFlow()
    fun editingKeep(keep: Keep) {
        viewModelScope.launch {
            _editingKeep.emit(keep)
        }
    }

    //recycler Data
//    private val _recyclerKeeps = MutableSharedFlow<List<Keep>>(
//        replay = 1,
//        onBufferOverflow = BufferOverflow.DROP_LATEST
//    ).shareIn(viewModelScope, started = SharingStarted.Lazily)


    //    val recyclerKeeps: Flow<List<Keep>> = flow {
//        emit(ArrayList(database.keepDao().getAll()))
//    }.shareIn(viewModelScope, started = SharingStarted.Lazily, 1)

    //добавление новой заметки
    fun addKeep(keep: Keep) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            database.keepDao().insertKeep(keep)
            fetchData()
        }
    }

    //удаление заметки
    fun deleteKeep(keep: Keep) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            database.keepDao().deleteKeep(keep)
            fetchData()

        }
    }

    //обновление заметки
    fun editKeep(keep: Keep) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            database.keepDao().updateKeep(keep.id, keep.keepTitle, keep.keepBody!!)
            fetchData()
        }
    }

    //обновление базы данных
    fun fetchData() {
//        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
//            _recyclerKeeps.tryEmit(ArrayList(database.keepDao().getAll()))
//        }
        viewModelScope.launch {
            _recyclerKeeps.emit(database.keepDao().getAll())
        }
    }

}