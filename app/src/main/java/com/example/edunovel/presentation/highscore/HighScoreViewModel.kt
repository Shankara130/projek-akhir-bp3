package com.example.edunovel.presentation.highscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.domain.model.Quiz
import com.example.edunovel.domain.usecase.quiz.GetHighScoresUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HighScoreViewModel(
    private val getHighScoresUseCase: GetHighScoresUseCase
) : ViewModel() {
    
    private val _highScores = MutableLiveData<Resource<List<Quiz>>>()
    val highScores: LiveData<Resource<List<Quiz>>> = _highScores
    
    init {
        loadHighScores()
    }
    
    fun loadHighScores() {
        getHighScoresUseCase().onEach { result ->
            _highScores.value = result
        }.launchIn(viewModelScope)
    }
}