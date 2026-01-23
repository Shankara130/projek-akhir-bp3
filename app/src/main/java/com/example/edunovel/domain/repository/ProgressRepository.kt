package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Progress
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    suspend fun insertProgress(progress: Progress): Long
    suspend fun updateProgress(progress: Progress)
    suspend fun deleteProgress(progress: Progress)
    fun getAllProgress(userId: Long): Flow<List<Progress>>
    suspend fun getProgressByChapter(userId: Long, chapterId: Int): Progress?
    fun getProgressBySubject(userId: Long, subject: String): Flow<List<Progress>>
    suspend fun deleteProgressById(progressId: Long)
}