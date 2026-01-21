package com.example.edunovel.domain.model

data class QuizQuestion(
    val id: Long = 0,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanation: String = "",
    val subject: String,
    val difficulty: String = "Medium"
)