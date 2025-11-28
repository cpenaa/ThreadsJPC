package com.example.jetpackcomposetemplate.di

import android.app.Application
import androidx.room.Room
import com.example.jetpackcomposetemplate.data.local.AppDatabase
import com.example.jetpackcomposetemplate.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()
}
