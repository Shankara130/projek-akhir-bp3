package com.example.edunovel.domain.model

data class Quiz(
    val id: Long = 0,
    val title: String,
    val subject: String,
    val questions: List<QuizQuestion>,
    val totalQuestions: Int,
    val passingScore: Int = 70
)