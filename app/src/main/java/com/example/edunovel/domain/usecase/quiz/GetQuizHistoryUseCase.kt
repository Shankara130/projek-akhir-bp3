package com.example.edunovel.domain.usecase.quiz

import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.repository.QuizRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetQuizHistoryUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<List<Quiz>>> {
        return repository.getAllQuizResults(userId)
            .map<List<Quiz>, Resource<List<Quiz>>> { Resource.Success(it) }
            .catch { emit(Resource.Error(it.localizedMessage ?: "Failed to load history")) }
    }
}