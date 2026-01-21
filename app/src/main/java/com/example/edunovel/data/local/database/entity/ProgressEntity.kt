package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val chapterId: Int,
    val subject: String,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val lastPosition: Int = 0, // Untuk resume story
    val updatedAt: Long = System.currentTimeMillis()
)