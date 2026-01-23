package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materials")
data class MaterialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subject: String,
    val chapterId: Int,
    val content: String,
    val type: String,
    val orderIndex: Int,
    val createdAt: Long
)