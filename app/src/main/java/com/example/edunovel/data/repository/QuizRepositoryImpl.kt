package com.example.edunovel.data.repository

import com.example.edunovel.data.local.database.dao.QuizDao
import com.example.edunovel.data.local.database.dao.QuizQuestionDao
import com.example.edunovel.data.local.database.entity.QuizEntity
import com.example.edunovel.data.local.database.entity.QuizQuestionEntity
import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.model.QuizQuestion
import com.example.edunovel.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(
    private val quizDao: QuizDao,
    private val quizQuestionDao: QuizQuestionDao
) : QuizRepository {
    
    override suspend fun insertQuizResult(quiz: Quiz): Long {
        return quizDao.insertQuizResult(quiz.toEntity())
    }
    
    override fun getAllQuizResults(userId: Int): Flow<List<Quiz>> {
        return quizDao.getAllQuizResults(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getHighestScore(userId: Int, subject: String): Quiz? {
        return quizDao.getHighestScore(userId, subject)?.toDomain()
    }
    
    override fun getTopScores(): Flow<List<Quiz>> {
        return quizDao.getTopScores().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun deleteQuizResult(quiz: Quiz) {
        quizDao.deleteQuizResult(quiz.toEntity())
    }
    
    override suspend fun insertQuestions(questions: List<QuizQuestion>) {
        quizQuestionDao.insertQuestions(questions.map { it.toEntity() })
    }
    
    override suspend fun getQuestionsBySubject(subject: String): List<QuizQuestion> {
        return quizQuestionDao.getQuestionsBySubject(subject)
            .map { entities -> entities.map { it.toDomain() } }
            .kotlinx.coroutines.flow.first()
    }
    
    override suspend fun getQuestionsByChapter(chapterId: Int): List<QuizQuestion> {
        return quizQuestionDao.getQuestionsByChapter(chapterId).map { it.toDomain() }
    }
    
    override suspend fun getRandomQuestions(subject: String, limit: Int): List<QuizQuestion> {
        return quizQuestionDao.getRandomQuestions(subject, limit).map { it.toDomain() }
    }
    
    private fun Quiz.toEntity() = QuizEntity(
        id = id,
        userId = userId,
        subject = subject,
        score = score,
        totalQuestions = totalQuestions,
        completedAt = completedAt
    )
    
    private fun QuizEntity.toDomain() = Quiz(
        id = id,
        userId = userId,
        subject = subject,
        score = score,
        totalQuestions = totalQuestions,
        completedAt = completedAt
    )
    
    private fun QuizQuestion.toEntity() = QuizQuestionEntity(
        id = id,
        subject = "",
        chapterId = 0,
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
        explanation = explanation
    )
}