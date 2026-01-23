package com.example.edunovel.domain.usecase.quiz

import com.example.edunovel.domain.model.QuizSession
import com.example.edunovel.domain.repository.QuizRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetHighScoresUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(): Flow<Resource<List<QuizSession>>> {
        return repository.getTopScores()
            .map<List<QuizSession>, Resource<List<QuizSession>>> { 
                Resource.Success(it) 
            }
            .catch { emit(Resource.Error(it.localizedMessage ?: "Failed to load high scores")) }
    }
}