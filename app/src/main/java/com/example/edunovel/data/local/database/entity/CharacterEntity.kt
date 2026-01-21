package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val name: String,
    val imageUri: String?, // Path to image
    val personality: String, // Friendly, Serious, Cheerful, etc
    val subject: String, // Math, Science, English, etc
    val description: String,
    val isDefault: Boolean = false, // Pre-loaded characters
    val createdAt: Long = System.currentTimeMillis()
)