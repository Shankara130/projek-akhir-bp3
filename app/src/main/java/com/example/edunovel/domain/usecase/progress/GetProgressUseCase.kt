package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.Flow

class GetProgressUseCase(
    private val progressRepository: ProgressRepository
) {
    operator fun invoke(userId: Long): Flow<List<Progress>> {
        return progressRepository.getProgressByUser(userId)
    }
}