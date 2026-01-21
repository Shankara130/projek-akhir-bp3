package com.example.edunovel.domain.model

data class Material(
    val id: Int = 0,
    val chapterId: Int,
    val subject: String,
    val title: String,
    val content: String,
    val characterId: Int,
    val dialogueText: String,
    val hasQuiz: Boolean = false,
    val orderIndex: Int = 0
)