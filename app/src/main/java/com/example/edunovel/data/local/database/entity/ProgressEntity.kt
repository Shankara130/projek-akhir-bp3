package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.edunovel.domain.model.Progress

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val chapterId: Int,
    val subject: String,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val lastPosition: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
)

fun ProgressEntity.toDomainModel(): Progress {
    return Progress(
        id = id,
        userId = userId,
        chapterId = chapterId,
        subject = subject,
        isCompleted = isCompleted,
        score = score,
        lastPosition = lastPosition,
        updatedAt = updatedAt
    )
}

fun Progress.toEntity(): ProgressEntity {
    return ProgressEntity(
        id = id,
        userId = userId,
        chapterId = chapterId,
        subject = subject,
        isCompleted = isCompleted,
        score = score,
        lastPosition = lastPosition,
        updatedAt = updatedAt
    )
}