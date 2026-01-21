package com.example.edunovel.domain.model

data class QuizSession(
    val id: Long = 0,
    val userId: Long,
    val quizId: Long,
    val score: Int = 0,
    val totalQuestions: Int,
    val correctAnswers: Int = 0,
    val completedAt: Long = System.currentTimeMillis(),
    val isPassed: Boolean = false
)