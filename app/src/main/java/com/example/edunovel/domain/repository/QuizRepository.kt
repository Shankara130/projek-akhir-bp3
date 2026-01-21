package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.model.QuizQuestion
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun insertQuizResult(quiz: Quiz): Long
    fun getAllQuizResults(userId: Int): Flow<List<Quiz>>
    suspend fun getHighestScore(userId: Int, subject: String): Quiz?
    fun getTopScores(): Flow<List<Quiz>>
    suspend fun deleteQuizResult(quiz: Quiz)
    
    // Quiz Questions
    suspend fun insertQuestions(questions: List<QuizQuestion>)
    suspend fun getQuestionsBySubject(subject: String): List<QuizQuestion>
    suspend fun getQuestionsByChapter(chapterId: Int): List<QuizQuestion>
    suspend fun getRandomQuestions(subject: String, limit: Int): List<QuizQuestion>
}