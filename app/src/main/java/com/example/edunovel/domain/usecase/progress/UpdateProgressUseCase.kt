package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateProgressUseCase(
    private val progressRepository: ProgressRepository
) {
    operator fun invoke(progress: Progress): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            progressRepository.updateProgress(progress)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to update progress"))
        }
    }
}