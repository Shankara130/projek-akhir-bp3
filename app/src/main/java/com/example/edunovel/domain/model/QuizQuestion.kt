package com.example.edunovel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int, // Index of correct option
    val explanation: String
) : Parcelable