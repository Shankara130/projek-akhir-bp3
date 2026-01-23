package com.example.edunovel.domain.usecase.progress

import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.repository.ProgressRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetAllProgressUseCase(
    private val repository: ProgressRepository
) {
    operator fun invoke(userId: Long): Flow<Resource<List<Progress>>> {
        return repository.getAllProgress(userId)
            .map<List<Progress>, Resource<List<Progress>>> { 
                Resource.Success(it) 
            }
            .catch { emit(Resource.Error(it.localizedMessage ?: "Failed to load progress")) }
    }
}