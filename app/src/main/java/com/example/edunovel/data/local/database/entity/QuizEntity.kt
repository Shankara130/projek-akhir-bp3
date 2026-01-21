package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.edunovel.data.local.database.Converters

@Entity(tableName = "quizzes")
@TypeConverters(Converters::class)
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val quizId: Long,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val completedAt: Long,
    val isPassed: Boolean
)