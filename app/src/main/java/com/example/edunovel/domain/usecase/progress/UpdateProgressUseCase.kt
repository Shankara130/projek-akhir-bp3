package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateProgressUseCase(
    private val progressRepository: ProgressRepository
) {
    operator fun invoke(progress: Progress): Flow<Resource<Unit>> {
        return progressRepository.updateProgress(progress)
    }
}