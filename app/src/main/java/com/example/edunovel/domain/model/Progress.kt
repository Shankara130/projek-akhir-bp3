package com.example.edunovel.domain.model

data class Progress(
    val id: Long = 0,
    val userId: Long,
    val subject: String,
    val chapterTitle: String,
    val completionPercentage: Int = 0,
    val lastAccessed: Long = System.currentTimeMillis(),
    val completed: Boolean = false
)