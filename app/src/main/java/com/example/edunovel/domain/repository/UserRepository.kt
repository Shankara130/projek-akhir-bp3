package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User): Long
    suspend fun login(username: String, password: String): User?
    suspend fun getUserByUsername(username: String): User?
    fun getUserById(userId: Int): Flow
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}