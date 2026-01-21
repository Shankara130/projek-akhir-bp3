package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materials")
data class MaterialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chapterId: Int,
    val subject: String,
    val title: String,
    val content: String,
    val characterId: Int, // Link to character
    val dialogueText: String,
    val hasQuiz: Boolean = false,
    val orderIndex: Int = 0
)