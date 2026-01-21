package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteProgressUseCase(
    private val progressRepository: ProgressRepository
) {
    operator fun invoke(progressId: Long): Flow<Resource<Unit>> {
        return progressRepository.deleteProgress(progressId)
    }
}