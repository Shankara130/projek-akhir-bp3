package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.edunovel.data.local.database.converters.StringListConverter

@Entity(tableName = "quiz_questions")
@TypeConverters(StringListConverter::class)
data class QuizQuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subject: String,
    val chapterId: Int,
    val question: String,
    val options: List<String>, // Will be converted by TypeConverter
    val correctAnswer: Int, // Index of correct option (0-3)
    val explanation: String,
    val difficulty: String = "Medium" // Easy, Medium, Hard
)