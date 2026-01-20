package com.example.edunovel.domain.model

data class StorySlide(
    val id: Int,
    val chapterId: Int,
    val character: Character?,
    val dialogueText: String,
    val content: String,
    val hasQuiz: Boolean = false,
    val orderIndex: Int
)