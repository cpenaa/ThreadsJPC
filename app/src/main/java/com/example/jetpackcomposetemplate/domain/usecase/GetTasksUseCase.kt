package com.example.jetpackcomposetemplate.domain.usecase

import com.example.jetpackcomposetemplate.data.model.Task
import com.example.jetpackcomposetemplate.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.tasks
    }
}
