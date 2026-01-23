package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteProgressUseCase(
    private val progressRepository: ProgressRepository
) {
    operator fun invoke(progressId: Long): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            progressRepository.deleteProgressById(progressId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to delete progress"))
        }
    }
}