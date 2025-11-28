package com.example.jetpackcomposetemplate.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.jetpackcomposetemplate.data.repository.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: TaskRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        println("🔧 Worker iniciado: TaskSyncWorker")

        return try {

            val success = repository.syncTasks()

            if (success) {
                println("Worker completado correctamente")
                Result.success()
            } else {
                println("Worker falló, pero es recuperable (retry)")
                Result.retry()
            }

        } catch (e: Exception) {
            println("Worker error fatal: ${e.message}")
            Result.retry()
        }
    }
}
