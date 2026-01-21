package com.example.edunovel.data.local.database.dao

import androidx.room.*
import com.example.edunovel.data.local.database.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity): Long
    
    @Update
    suspend fun updateProgress(progress: ProgressEntity)
    
    @Delete
    suspend fun deleteProgress(progress: ProgressEntity)
    
    @Query("SELECT * FROM progress WHERE userId = :userId")
    fun getAllProgress(userId: Int): Flow<List>
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND chapterId = :chapterId")
    suspend fun getProgressByChapter(userId: Int, chapterId: Int): ProgressEntity?
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND subject = :subject")
    fun getProgressBySubject(userId: Int, subject: String): Flow<List>
    
    @Query("DELETE FROM progress WHERE id = :progressId")
    suspend fun deleteProgressById(progressId: Int)
}