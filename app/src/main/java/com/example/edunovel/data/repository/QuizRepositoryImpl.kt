package com.example.edunovel.data.repository

import com.example.edunovel.data.local.database.dao.QuizDao
import com.example.edunovel.data.local.database.dao.QuizQuestionDao
import com.example.edunovel.data.local.database.entity.QuizEntity
import com.example.edunovel.data.local.database.entity.QuizQuestionEntity
import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.model.QuizQuestion
import com.example.edunovel.domain.model.QuizSession
import com.example.edunovel.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(
    private val quizDao: QuizDao,
    private val quizQuestionDao: QuizQuestionDao
) : QuizRepository {
    
    override suspend fun insertQuizResult(quizSession: QuizSession): Long {
        return quizDao.insertQuizResult(quizSession.toEntity())
    }
    
    override fun getAllQuizResults(userId: Long): Flow<List<QuizSession>> {
        return quizDao.getAllQuizResults(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getHighestScore(userId: Long, subject: String): QuizSession? {
        return quizDao.getHighestScore(userId, subject)?.toDomain()
    }
    
    override fun getTopScores(): Flow<List<QuizSession>> {
        return quizDao.getTopScores().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun deleteQuizResult(quizSession: QuizSession) {
        quizDao.deleteQuizResult(quizSession.toEntity())
    }
    
    override suspend fun insertQuestions(questions: List<QuizQuestion>) {
        quizQuestionDao.insertQuestions(questions.map { it.toEntity() })
    }
    
    override suspend fun getQuestionsBySubject(subject: String): List<QuizQuestion> {
        return quizQuestionDao.getQuestionsBySubject(subject).map { it.toDomain() }
    }
    
    override suspend fun getQuestionsByChapter(chapterId: Int): List<QuizQuestion> {
        return quizQuestionDao.getQuestionsByChapter(chapterId).map { it.toDomain() }
    }
    
    override suspend fun getRandomQuestions(subject: String, limit: Int): List<QuizQuestion> {
        return quizQuestionDao.getRandomQuestions(subject, limit).map { it.toDomain() }
    }
    
    private fun QuizSession.toEntity() = QuizEntity(
        id = id,
        userId = userId,
        quizId = quizId,
        subject = subject,
        score = score,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        completedAt = completedAt,
        isPassed = isPassed
    )
    
    private fun QuizEntity.toDomain() = QuizSession(
        id = id,
        userId = userId,
        quizId = quizId,
        subject = subject,
        score = score,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        completedAt = completedAt,
        isPassed = isPassed
    )
    
    private fun QuizQuestion.toEntity() = QuizQuestionEntity(
        id = id,
        subject = subject,
        chapterId = 0, // Should be passed if available
        question = question,
        options = options,
        correctAnswer = correctAnswer,
        explanation = explanation
    )
    
    private fun QuizQuestionEntity.toDomain() = QuizQuestion(
        id = id,
        question = question,
        options = options,
        correctAnswer = correctAnswer,
        explanation = explanation,
        subject = subject
    )
}