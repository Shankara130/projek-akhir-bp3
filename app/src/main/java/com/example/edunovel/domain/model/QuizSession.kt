package com.example.edunovel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizSession(
    val id: Long = 0,
    val userId: Long,
    val quizId: Long,
    val subject: String,
    val score: Int = 0,
    val totalQuestions: Int,
    val correctAnswers: Int = 0,
    val completedAt: Long = System.currentTimeMillis(),
    val isPassed: Boolean = false
) : Parcelable