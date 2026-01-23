package com.example.edunovel.domain.usecase.story

import com.example.edunovel.domain.model.Material
import com.example.edunovel.domain.repository.MaterialRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStoryContentUseCase(
    private val materialRepository: MaterialRepository
) {
    operator fun invoke(chapterId: Int): Flow<Resource<List<Material>>> = flow {
        try {
            emit(Resource.Loading)
            
            val materials = materialRepository.getMaterialsByChapter(chapterId)
            emit(Resource.Success(materials))
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to load story"))
        }
    }
}