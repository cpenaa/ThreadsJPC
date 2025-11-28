package com.example.jetpackcomposetemplate.di

import com.example.jetpackcomposetemplate.domain.usecase.ProcessTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideProcessTasksUseCase(): ProcessTasksUseCase =
        ProcessTasksUseCase()
}
