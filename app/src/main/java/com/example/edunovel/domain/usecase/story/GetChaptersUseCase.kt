package com.example.edunovel.domain.usecase.story

import com.example.edunovel.domain.model.StoryChapter
import com.example.edunovel.domain.repository.MaterialRepository
import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChaptersUseCase(
    private val materialRepository: MaterialRepository,
    private val progressRepository: ProgressRepository
) {
    operator fun invoke(userId: Int, subject: String): Flow<Resource<List<StoryChapter>>> = flow {
        try {
            emit(Resource.Loading())
            
            // Get materials grouped by chapter
            val materials = materialRepository.getMaterialsBySubject(subject)
            
            // Get user progress
            val progressList = progressRepository.getProgressBySubject(userId, subject)
            
            // TODO: Transform to StoryChapter list
            // For now, return empty list
            emit(Resource.Success(emptyList()))
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to load chapters"))
        }
    }
}