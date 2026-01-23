package com.example.edunovel.data.repository

import com.example.edunovel.data.local.database.dao.ProgressDao
import com.example.edunovel.data.local.database.entity.ProgressEntity
import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgressRepositoryImpl(
    private val progressDao: ProgressDao
) : ProgressRepository {
    
    override suspend fun insertProgress(progress: Progress): Long {
        return progressDao.insertProgress(progress.toEntity())
    }
    
    override suspend fun updateProgress(progress: Progress) {
        progressDao.updateProgress(progress.toEntity())
    }
    
    override suspend fun deleteProgress(progress: Progress) {
        progressDao.deleteProgress(progress.toEntity())
    }
    
    override fun getAllProgress(userId: Long): Flow<List<Progress>> {
        return progressDao.getAllProgress(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getProgressByChapter(userId: Long, chapterId: Int): Progress? {
        return progressDao.getProgressByChapter(userId, chapterId)?.toDomain()
    }
    
    override fun getProgressBySubject(userId: Long, subject: String): Flow<List<Progress>> {
        return progressDao.getProgressBySubject(userId, subject).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun deleteProgressById(progressId: Long) {
        progressDao.deleteProgressById(progressId)
    }
    
    private fun Progress.toEntity() = ProgressEntity(
        id = id,
        userId = userId,
        chapterId = chapterId,
        subject = subject,
        isCompleted = isCompleted,
        score = score,
        lastPosition = lastPosition,
        updatedAt = updatedAt
    )
    
    private fun ProgressEntity.toDomain() = Progress(
        id = id,
        userId = userId,
        chapterId = chapterId,
        subject = subject,
        isCompleted = isCompleted,
        score = score,
        lastPosition = lastPosition,
        updatedAt = updatedAt
    )
}