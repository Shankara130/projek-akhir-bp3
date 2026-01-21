package com.example.edunovel.presentation.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.model.QuizQuestion
import com.example.edunovel.domain.model.QuizSession
import com.example.edunovel.domain.usecase.quiz.GetQuizQuestionsUseCase
import com.example.edunovel.domain.usecase.quiz.SaveQuizResultUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase,
    private val saveQuizResultUseCase: SaveQuizResultUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    private val _quizState = MutableLiveData<Resource<List<QuizQuestion>>>()
    val quizState: LiveData<Resource<List<QuizQuestion>>> = _quizState
    
    private val _quizSession = MutableLiveData<QuizSession>()
    val quizSession: LiveData<QuizSession> = _quizSession
    
    private val _saveResultState = MutableLiveData<Resource<Long>>()
    val saveResultState: LiveData<Resource<Long>> = _saveResultState
    
    private var currentUserId: Int = 0
    
    init {
        viewModelScope.launch {
            userPreferences.userSession.collect { session ->
                currentUserId = session.userId
            }
        }
    }
    
    fun loadQuiz(subject: String, questionCount: Int = 10) {
        getQuizQuestionsUseCase(subject, questionCount).onEach { result ->
            _quizState.value = result
            
            if (result is Resource.Success && result.data != null) {
                _quizSession.value = QuizSession(
                    subject = subject,
                    questions = result.data
                )
            }
        }.launchIn(viewModelScope)
    }
    
    fun answerQuestion(answerIndex: Int) {
        val session = _quizSession.value ?: return
        
        val newAnswers = session.userAnswers.toMutableList()
        newAnswers[session.currentQuestionIndex] = answerIndex
        
        _quizSession.value = session.copy(userAnswers = newAnswers)
    }
    
    fun nextQuestion() {
        val session = _quizSession.value ?: return
        
        if (session.currentQuestionIndex < session.getTotalQuestions() - 1) {
            _quizSession.value = session.copy(
                currentQuestionIndex = session.currentQuestionIndex + 1
            )
        }
    }
    
    fun previousQuestion() {
        val session = _quizSession.value ?: return
        
        if (session.currentQuestionIndex > 0) {
            _quizSession.value = session.copy(
                currentQuestionIndex = session.currentQuestionIndex - 1
            )
        }
    }
    
    fun finishQuiz() {
        val session = _quizSession.value ?: return
        
        val score = session.calculateScore()
        val quiz = Quiz(
            userId = currentUserId,
            subject = session.subject,
            score = score,
            totalQuestions = session.getTotalQuestions()
        )
        
        saveQuizResultUseCase(quiz).onEach { result ->
            _saveResultState.value = result
        }.launchIn(viewModelScope)
    }
    
    fun getCurrentAnswer(): Int? {
        val session = _quizSession.value ?: return null
        return session.userAnswers.getOrNull(session.currentQuestionIndex)
    }
}