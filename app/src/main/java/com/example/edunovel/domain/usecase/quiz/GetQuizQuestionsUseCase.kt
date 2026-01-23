package com.example.edunovel.domain.usecase.quiz

import com.example.edunovel.domain.model.QuizQuestion
import com.example.edunovel.domain.repository.QuizRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuizQuestionsUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(subject: String, questionCount: Int = 10): Flow<Resource<List<QuizQuestion>>> = flow {
        try {
            emit(Resource.Loading)
            
            val questions = repository.getRandomQuestions(subject, questionCount)
            
            if (questions.isEmpty()) {
                emit(Resource.Error("No questions available for this subject"))
            } else {
                emit(Resource.Success(questions))
            }
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to load questions"))
        }
    }
}