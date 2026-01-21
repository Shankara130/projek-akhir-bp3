package com.example.edunovel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizSession(
    val subject: String,
    val questions: List<QuizQuestion>,
    val currentQuestionIndex: Int = 0,
    val userAnswers: List<Int?> = List(questions.size) { null },
    val score: Int = 0,
    val isCompleted: Boolean = false
) : Parcelable {
    
    fun getCurrentQuestion(): QuizQuestion? {
        return questions.getOrNull(currentQuestionIndex)
    }
    
    fun getTotalQuestions(): Int = questions.size
    
    fun getAnsweredCount(): Int = userAnswers.count { it != null }
    
    fun getCorrectCount(): Int {
        return questions.zip(userAnswers).count { (question, answer) ->
            answer != null && answer == question.correctAnswer
        }
    }
    
    fun calculateScore(): Int {
        val correct = getCorrectCount()
        val total = getTotalQuestions()
        return if (total > 0) (correct * 100) / total else 0
    }
}