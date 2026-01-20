package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveProgressUseCase(
    private val repository: ProgressRepository
) {
    operator fun invoke(progress: Progress): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading())
            
            // Check if progress already exists
            val existing = repository.getProgressByChapter(progress.userId, progress.chapterId)
            
            if (existing != null) {
                // Update existing progress
                val updated = existing.copy(
                    lastPosition = progress.lastPosition,
                    isCompleted = progress.isCompleted,
                    score = maxOf(existing.score, progress.score),
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateProgress(updated)
                emit(Resource.Success(existing.id.toLong()))
            } else {
                // Insert new progress
                val id = repository.insertProgress(progress)
                emit(Resource.Success(id))
            }
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to save progress"))
        }
    }
}