package com.example.jetpackcomposetemplate.domain.usecase

import com.example.jetpackcomposetemplate.data.model.Task
import com.example.jetpackcomposetemplate.data.repository.TaskRepository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(title: String) {
        repository.addTask(title)
    }
}
