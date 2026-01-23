package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.model.QuizQuestion
import com.example.edunovel.domain.model.QuizSession
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun insertQuizResult(quizSession: QuizSession): Long
    fun getAllQuizResults(userId: Long): Flow<List<QuizSession>>
    suspend fun getHighestScore(userId: Long, subject: String): QuizSession?
    fun getTopScores(): Flow<List<QuizSession>>
    suspend fun deleteQuizResult(quizSession: QuizSession)
    
    // Quiz Questions
    suspend fun insertQuestions(questions: List<QuizQuestion>)
    suspend fun getQuestionsBySubject(subject: String): List<QuizQuestion>
    suspend fun getQuestionsByChapter(chapterId: Int): List<QuizQuestion>
    suspend fun getRandomQuestions(subject: String, limit: Int): List<QuizQuestion>
}