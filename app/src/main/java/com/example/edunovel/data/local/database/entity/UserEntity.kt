package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.edunovel.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val email: String,
    val password: String,
    val fullName: String,
    val avatarUrl: String,
    val createdAt: Long
)

fun UserEntity.toDomainModel(): User {
    return User(
        id = id,
        username = username,
        email = email,
        password = password,
        fullName = fullName,
        avatarUrl = avatarUrl,
        createdAt = createdAt
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        password = password,
        fullName = fullName,
        avatarUrl = avatarUrl,
        createdAt = createdAt
    )
}