package com.example.edunovel.domain.model

data class Character(
    val id: Long = 0,
    val name: String,
    val subject: String,
    val personality: String,
    val description: String = "",
    val imageUrl: String = "",
    val userId: Long,
    val createdAt: Long = System.currentTimeMillis()
)