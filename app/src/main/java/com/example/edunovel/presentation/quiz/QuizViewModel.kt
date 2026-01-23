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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase,
    private val saveQuizResultUseCase: SaveQuizResultUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    private val _quizState = MutableLiveData<Resource<List<QuizQuestion>>>()
    val quizState: LiveData<Resource<List<QuizQuestion>>> = _quizState
    
    // UI-specific session state
    private val _currentQuestionIndex = MutableLiveData<Int>(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex
    
    private val _userAnswers = MutableLiveData<MutableList<Int?>>()
    
    private val _saveResultState = MutableLiveData<Resource<Long>>()
    val saveResultState: LiveData<Resource<Long>> = _saveResultState
    
    private val _finishedQuizSession = MutableLiveData<QuizSession>()
    val finishedQuizSession: LiveData<QuizSession> = _finishedQuizSession
    
    private var currentUserId: Long = 0L
    private var subject: String = ""
    
    init {
        viewModelScope.launch {
            userPreferences.userSession.collect { session ->
                currentUserId = session.userId
            }
        }
    }
    
    fun loadQuiz(subject: String, questionCount: Int = 10) {
        this.subject = subject
        getQuizQuestionsUseCase(subject, questionCount).onEach { result ->
            _quizState.value = result
            
            if (result is Resource.Success && result.data != null) {
                _userAnswers.value = MutableList(result.data.size) { null }
                _currentQuestionIndex.value = 0
            }
        }.launchIn(viewModelScope)
    }
    
    fun answerQuestion(answerIndex: Int) {
        val answers = _userAnswers.value ?: return
        val index = _currentQuestionIndex.value ?: return
        
        answers[index] = answerIndex
        _userAnswers.value = answers
    }
    
    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value ?: return
        val questions = _quizState.value?.let { (it as? Resource.Success)?.data } ?: return
        
        if (currentIndex < questions.size - 1) {
            _currentQuestionIndex.value = currentIndex + 1
        }
    }
    
    fun previousQuestion() {
        val currentIndex = _currentQuestionIndex.value ?: return
        
        if (currentIndex > 0) {
            _currentQuestionIndex.value = currentIndex - 1
        }
    }
    
    fun finishQuiz() {
        val questions = _quizState.value?.let { (it as? Resource.Success)?.data } ?: return
        val answers = _userAnswers.value ?: return
        
        var correctCount = 0
        questions.forEachIndexed { index, question ->
            if (answers[index] == question.correctAnswer) {
                correctCount++
            }
        }
        
        val score = if (questions.isNotEmpty()) (correctCount * 100) / questions.size else 0
        
        val quizSession = QuizSession(
            userId = currentUserId,
            quizId = 0, // Placeholder
            subject = subject,
            score = score,
            totalQuestions = questions.size,
            correctAnswers = correctCount,
            isPassed = score >= 70
        )
        
        _finishedQuizSession.value = quizSession
        saveQuizResultUseCase(quizSession).onEach { result ->
            _saveResultState.value = result
        }.launchIn(viewModelScope)
    }
    
    fun getCurrentAnswer(): Int? {
        val index = _currentQuestionIndex.value ?: return null
        return _userAnswers.value?.getOrNull(index)
    }
}