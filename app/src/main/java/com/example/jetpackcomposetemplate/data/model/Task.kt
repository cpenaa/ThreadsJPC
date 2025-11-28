package com.example.jetpackcomposetemplate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
