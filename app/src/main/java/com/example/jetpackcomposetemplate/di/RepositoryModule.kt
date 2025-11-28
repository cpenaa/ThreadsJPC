package com.example.jetpackcomposetemplate.di

import com.example.jetpackcomposetemplate.data.local.TaskDao
import com.example.jetpackcomposetemplate.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository =
        TaskRepository(taskDao)
}
