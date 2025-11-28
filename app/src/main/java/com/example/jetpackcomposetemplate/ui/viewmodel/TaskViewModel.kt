package com.example.jetpackcomposetemplate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetemplate.data.model.Task
import com.example.jetpackcomposetemplate.data.repository.TaskRepository
import com.example.jetpackcomposetemplate.sync.SyncScheduler
import com.example.jetpackcomposetemplate.ui.viewmodel.SyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val syncScheduler: SyncScheduler
) : ViewModel() {

    // -------------------------------------------------------------
    // ESTADO DE SINCRONIZACIÓN (barra superior)
    // -------------------------------------------------------------
    private val _syncState = MutableStateFlow(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    // -------------------------------------------------------------
    // MANEJO DE ESTADO DE SINCRONIZACIÓN (UI)
    // -------------------------------------------------------------
    fun setSyncState(state: SyncState) {
        _syncState.value = state

        // Limpieza automática después de mostrar feedback
        viewModelScope.launch {
            when (state) {
                SyncState.Success -> {
                    kotlinx.coroutines.delay(2500)
                    _syncState.value = SyncState.Idle
                }
                SyncState.Error -> {
                    kotlinx.coroutines.delay(3000)
                    _syncState.value = SyncState.Idle
                }
                else -> Unit
            }
        }
    }

    // -------------------------------------------------------------
    // STATE: Lista de tareas en vivo
    // -------------------------------------------------------------
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // -------------------------------------------------------------
    // Estado del One-Time Sync (WorkManager)
    // -------------------------------------------------------------
    val syncWorkInfo = syncScheduler.getOneTimeSyncWorkInfoLiveData()

    init {
        observeTasks()
    }

    // -------------------------------------------------------------
    // OBSERVAR CAMBIOS EN ROOM
    // -------------------------------------------------------------
    private fun observeTasks() {
        viewModelScope.launch {
            repository.tasks.collect { list ->
                _tasks.value = list
            }
        }
    }


    // -------------------------------------------------------------
    // CRUD
    // -------------------------------------------------------------
    fun addTask(title: String) {
        viewModelScope.launch {
            repository.addTask(title)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            repository.deleteTask(id)
        }
    }
    fun updateSyncState(state: SyncState) {
        _syncState.value = state
    }

    // -------------------------------------------------------------
    // SINCRONIZAR: ONE-TIME REQUEST
    // -------------------------------------------------------------
    fun syncTasks() {
        setSyncState(SyncState.Syncing)
        syncScheduler.scheduleOneTimeSync()
    }
}
