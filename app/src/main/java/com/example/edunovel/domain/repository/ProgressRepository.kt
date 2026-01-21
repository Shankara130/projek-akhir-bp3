package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Progress
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    suspend fun insertProgress(progress: Progress): Long
    suspend fun updateProgress(progress: Progress)
    suspend fun deleteProgress(progress: Progress)
    fun getAllProgress(userId: Int): Flow<List>
    suspend fun getProgressByChapter(userId: Int, chapterId: Int): Progress?
    fun getProgressBySubject(userId: Int, subject: String): Flow<List>
    suspend fun deleteProgressById(progressId: Int)
}