package com.example.edunovel.data.local.database.dao

import androidx.room.*
import com.example.edunovel.data.local.database.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(quiz: QuizEntity): Long
    
    @Query("SELECT * FROM quiz_results WHERE userId = :userId ORDER BY completedAt DESC")
    fun getAllQuizResults(userId: Long): Flow<List<QuizEntity>>
    
    @Query("SELECT * FROM quiz_results WHERE userId = :userId AND subject = :subject ORDER BY score DESC LIMIT 1")
    suspend fun getHighestScore(userId: Long, subject: String): QuizEntity?
    
    @Query("SELECT * FROM quiz_results ORDER BY score DESC LIMIT 10")
    fun getTopScores(): Flow<List<QuizEntity>>
    
    @Delete
    suspend fun deleteQuizResult(quiz: QuizEntity)
}