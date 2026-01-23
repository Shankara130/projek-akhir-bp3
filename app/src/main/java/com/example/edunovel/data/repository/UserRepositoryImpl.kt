package com.example.edunovel.data.repository

import com.example.edunovel.data.local.database.dao.UserDao
import com.example.edunovel.data.local.database.entity.UserEntity
import com.example.edunovel.domain.model.User
import com.example.edunovel.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    
    override suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user.toEntity())
    }
    
    override suspend fun login(username: String, password: String): User? {
        return userDao.login(username, password)?.toDomain()
    }
    
    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)?.toDomain()
    }
    
    override fun getUserById(userId: Long): Flow<User?> {
        return userDao.getUserById(userId).map { it?.toDomain() }
    }
    
    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toEntity())
    }
    
    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.toEntity())
    }
    
    private fun User.toEntity() = UserEntity(
        id = id,
        username = username,
        password = password,
        email = email,
        fullName = fullName,
        avatarUrl = avatarUrl,
        createdAt = createdAt
    )
    
    private fun UserEntity.toDomain() = User(
        id = id,
        username = username,
        password = password,
        email = email,
        fullName = fullName,
        avatarUrl = avatarUrl,
        createdAt = createdAt
    )
}