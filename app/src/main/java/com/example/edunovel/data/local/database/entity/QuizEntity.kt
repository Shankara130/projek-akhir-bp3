package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.edunovel.data.local.database.converters.StringListConverter

@Entity(tableName = "quiz_results")
@TypeConverters(StringListConverter::class)
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val quizId: Long,
    val subject: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val completedAt: Long,
    val isPassed: Boolean
)