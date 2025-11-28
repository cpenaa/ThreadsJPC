package com.example.jetpackcomposetemplate.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.jetpackcomposetemplate.workers.TaskSyncWorker
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * SyncScheduler: helper para programar y controlar WorkManager jobs relacionados a sincronización.
 * - Usa WorkManager para OneTime y Periodic
 * - Expone métodos para encolar y cancelar
 * - Devuelve LiveData de WorkInfo cuando es necesario desde la UI
 */

@Singleton
class SyncScheduler @Inject constructor(
    private val workManager: WorkManager
) {

    companion object {
        const val UNIQUE_PERIODIC_SYNC_WORK_NAME = "unique_periodic_task_sync"
        const val UNIQUE_ONE_TIME_SYNC_WORK_NAME = "unique_one_time_task_sync"
    }

    /**
     * Crea constraints por defecto (requiere red conectada, evita batería baja por defecto si param = true)
     */
    private fun defaultConstraints(requireUnmetered: Boolean = false): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (requireUnmetered) setRequiredNetworkType(NetworkType.UNMETERED)
            }
            .build()
    }

    /**
     * Encola un PeriodicWorkRequest para sincronizar cada `repeatIntervalMinutes` minutos.
     * Nota: WorkManager solo permite periodos >= 15 minutes, el valor por defecto aquí es 15.
     */
    fun schedulePeriodicSync(repeatIntervalMinutes: Long = 15, requireUnmetered: Boolean = false) {
        val constraints = defaultConstraints(requireUnmetered)

        val periodicRequest = PeriodicWorkRequestBuilder<TaskSyncWorker>(
            repeatIntervalMinutes, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_PERIODIC_SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

    /**
     * Encola un OneTimeWorkRequest para una sincronización inmediata.
     * Si quieres pasar datos al Worker, agrégalos como InputData.
     */
    fun scheduleOneTimeSync(inputData: Data? = null, requireUnmetered: Boolean = false) {
        val constraints = defaultConstraints(requireUnmetered)

        val builder = OneTimeWorkRequestBuilder<TaskSyncWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)

        inputData?.let { builder.setInputData(it) }

        val request = builder.build()

        workManager.enqueueUniqueWork(
            UNIQUE_ONE_TIME_SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    /**
     * Cancela la sincronización periódica
     */
    fun cancelPeriodicSync() {
        workManager.cancelUniqueWork(UNIQUE_PERIODIC_SYNC_WORK_NAME)
    }

    /**
     * Cancela la sincronización one-time (si está encolada)
     */
    fun cancelOneTimeSync() {
        workManager.cancelUniqueWork(UNIQUE_ONE_TIME_SYNC_WORK_NAME)
    }

    /**
     * Observador LiveData para la UI que devuelve estados (ENQUEUED, RUNNING, SUCCEEDED, FAILED)
     */
    fun getPeriodicSyncWorkInfoLiveData() =
        workManager.getWorkInfosForUniqueWorkLiveData(UNIQUE_PERIODIC_SYNC_WORK_NAME)

    fun getOneTimeSyncWorkInfoLiveData() =
        workManager.getWorkInfosForUniqueWorkLiveData(UNIQUE_ONE_TIME_SYNC_WORK_NAME)
}
