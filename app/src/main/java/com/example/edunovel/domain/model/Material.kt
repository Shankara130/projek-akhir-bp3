package com.example.edunovel.domain.model

data class Material(
    val id: Long = 0,
    val title: String,
    val subject: String,
    val content: String,
    val type: String, // "story", "lesson", "exercise"
    val order: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)