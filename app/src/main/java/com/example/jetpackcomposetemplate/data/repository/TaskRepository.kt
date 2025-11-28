package com.example.jetpackcomposetemplate.data.repository

import com.example.jetpackcomposetemplate.data.local.TaskDao
import com.example.jetpackcomposetemplate.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TaskRepository(
    private val taskDao: TaskDao
) {

    // Flujo reactivo para UI
    val tasks: Flow<List<Task>> = taskDao.getAllTasks()

    // Crear tarea
    suspend fun addTask(title: String) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(Task(title = title))
        }
    }

    // Actualizar tarea
    suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    // Eliminar tarea
    suspend fun deleteTask(id: Int) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(id)
        }
    }

    // -------------------------------------------------------------------------
    //  FUNCIÓN NUEVA: Sincronización con servidor remoto (simulación)
    // -------------------------------------------------------------------------
    suspend fun syncTasks(): Boolean = withContext(Dispatchers.IO) {

        // 1. Obtener todas las tareas locales
        val localTasks = taskDao.getAllTasksList()

        println("🔄 Iniciando sincronización: ${localTasks.size} tareas locales")

        // 2. Simular latencia de red
        delay(1500)

        // 3. Simular subida de datos
        println("📤 Subiendo tareas al servidor...")

        delay(1000)

        // 4. Simular descarga del servidor
        println("📥 Descargando tareas actualizadas...")

        delay(1000)

        // 5. Ejemplo: aquí podrías combinar datos
        // En este demo no modificamos nada, solo confirmamos éxito.

        println("✅ Sincronización completada correctamente")

        return@withContext true
    }
}
