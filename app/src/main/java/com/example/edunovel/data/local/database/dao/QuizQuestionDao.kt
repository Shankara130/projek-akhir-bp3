package com.example.edunovel.data.local.database.dao

import androidx.room.*
import com.example.edunovel.data.local.database.entity.QuizQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizQuestionDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuizQuestionEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestionEntity>)
    
    @Query("SELECT * FROM quiz_questions WHERE subject = :subject")
    suspend fun getQuestionsBySubject(subject: String): List<QuizQuestionEntity>
    
    @Query("SELECT * FROM quiz_questions WHERE chapterId = :chapterId")
    suspend fun getQuestionsByChapter(chapterId: Int): List<QuizQuestionEntity>
    
    @Query("SELECT * FROM quiz_questions WHERE subject = :subject ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomQuestions(subject: String, limit: Int): List<QuizQuestionEntity>
    
    @Query("DELETE FROM quiz_questions WHERE id = :questionId")
    suspend fun deleteQuestion(questionId: Long)
}