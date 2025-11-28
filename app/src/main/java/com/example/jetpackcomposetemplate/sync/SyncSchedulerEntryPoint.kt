package com.example.jetpackcomposetemplate.sync

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SyncSchedulerEntryPoint {
    fun syncScheduler(): SyncScheduler
}
