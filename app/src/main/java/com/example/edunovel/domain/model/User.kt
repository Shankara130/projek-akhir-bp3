package com.example.edunovel.domain.model

data class User(
    val id: Long = 0,
    val username: String,
    val email: String,
    val password: String,
    val fullName: String = "",
    val avatarUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)