package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.edunovel.domain.model.Progress

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val subject: String,
    val chapterTitle: String,
    val completionPercentage: Int,
    val lastAccessed: Long,
    val completed: Boolean
)

fun ProgressEntity.toDomainModel(): Progress {
    return Progress(
        id = id,
        userId = userId,
        subject = subject,
        chapterTitle = chapterTitle,
        completionPercentage = completionPercentage,
        lastAccessed = lastAccessed,
        completed = completed
    )
}

fun Progress.toEntity(): ProgressEntity {
    return ProgressEntity(
        id = id,
        userId = userId,
        subject = subject,
        chapterTitle = chapterTitle,
        completionPercentage = completionPercentage,
        lastAccessed = lastAccessed,
        completed = completed
    )
}