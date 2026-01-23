package com.example.edunovel.domain.model

data class Progress(
    val id: Long = 0,
    val userId: Long,
    val chapterId: Int,
    val subject: String,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val lastPosition: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
)