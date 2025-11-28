package com.example.jetpackcomposetemplate.domain.usecase

import com.example.jetpackcomposetemplate.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProcessTasksUseCase {

    /**
     * Simula procesamiento pesado.
     * Aquí podrías agregar lógica real: enviar datos a servidor, filtros, cálculos, etc.
     */
    suspend fun process(tasks: List<Task>): List<Task> = withContext(Dispatchers.Default) {
        // Simulación de tarea pesada
        delay(600)
        tasks.map { it.copy(title = it.title.uppercase()) }
    }
}