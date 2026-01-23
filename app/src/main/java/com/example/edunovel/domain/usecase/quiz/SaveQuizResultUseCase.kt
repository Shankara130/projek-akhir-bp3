package com.example.edunovel.domain.usecase.quiz

import com.example.edunovel.domain.model.QuizSession
import com.example.edunovel.domain.repository.QuizRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveQuizResultUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(quizSession: QuizSession): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading)
            
            val id = repository.insertQuizResult(quizSession)
            emit(Resource.Success(id))
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to save quiz result"))
        }
    }
}