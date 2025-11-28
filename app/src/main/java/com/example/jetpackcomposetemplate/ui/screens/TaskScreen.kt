package com.example.jetpackcomposetemplate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.work.WorkInfo
import com.example.jetpackcomposetemplate.ui.viewmodel.TaskViewModel
import com.example.jetpackcomposetemplate.data.model.Task
import com.example.jetpackcomposetemplate.ui.components.TaskItem
import com.example.jetpackcomposetemplate.ui.components.TaskDialog
import com.example.jetpackcomposetemplate.ui.components.SyncStatusBar
import com.example.jetpackcomposetemplate.ui.viewmodel.SyncState

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel()
) {

    // Tasks desde el ViewModel
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())

    // Estado de sincronización declarado en el ViewModel (source of truth cuando WorkManager no provee info)
    val vmSyncState by viewModel.syncState.collectAsState()

    // LiveData<List<WorkInfo>> -> State<List<WorkInfo>?> (puede ser null)
    // observeAsState requires an explicit initial value here (null)
    val workInfoList: List<WorkInfo>? by viewModel.syncWorkInfo.observeAsState(initial = null)

    // Tomamos el primer WorkInfo si existe (la API devuelve lista)
    val workInfo: WorkInfo? = workInfoList?.firstOrNull()

    // Derivamos un estado de sincronización que prioriza el estado del WorkManager si está presente,
    // en caso contrario usamos el estado expuesto por el ViewModel.
    val displaySyncState: SyncState = when (workInfo?.state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.RUNNING -> SyncState.Syncing

        WorkInfo.State.SUCCEEDED -> SyncState.Success

        WorkInfo.State.FAILED -> SyncState.Error

        WorkInfo.State.CANCELLED -> SyncState.Idle

        else -> vmSyncState
    }

    var showDialog by remember { mutableStateOf(false) }
    var taskBeingEdited by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Barra de estado (usa el estado derivado)
        SyncStatusBar(displaySyncState)

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Mis Tareas",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón: nueva tarea
            Button(
                onClick = {
                    taskBeingEdited = null
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar tarea")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón: sincronizar (lanza la lógica del ViewModel,
            // que a su vez ejecuta repo.syncTasks() y schedulea el Worker)
            Button(
                onClick = { viewModel.syncTasks() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sincronizar ahora")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de tareas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onEdit = {
                            taskBeingEdited = task
                            showDialog = true
                        },
                        onDelete = {
                            viewModel.deleteTask(task.id)
                        }
                    )
                }
            }
        }
    }

    // Diálogo: crear / editar
    if (showDialog) {
        TaskDialog(
            initialTask = taskBeingEdited,
            onDismiss = { showDialog = false },
            onConfirm = { title ->
                if (taskBeingEdited == null) {
                    viewModel.addTask(title)
                } else {
                    viewModel.updateTask(taskBeingEdited!!.copy(title = title))
                }
                showDialog = false
            }
        )
    }
}
