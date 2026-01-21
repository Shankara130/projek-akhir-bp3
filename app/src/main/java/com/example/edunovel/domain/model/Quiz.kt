package com.example.edunovel.domain.model

data class Quiz(
    val id: Int = 0,
    val userId: Int,
    val subject: String,
    val score: Int,
    val totalQuestions: Int,
    val completedAt: Long = System.currentTimeMillis()
)