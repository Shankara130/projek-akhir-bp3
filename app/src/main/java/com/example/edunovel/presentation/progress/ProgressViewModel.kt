package com.example.edunovel.presentation.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.domain.model.Progress
import com.example.edunovel.domain.usecase.progress.GetAllProgressUseCase
import com.example.edunovel.domain.usecase.progress.DeleteProgressUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val getAllProgressUseCase: GetAllProgressUseCase,
    private val deleteProgressUseCase: DeleteProgressUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    private val _progressList = MutableLiveData<Resource<List<Progress>>>()
    val progressList: LiveData<Resource<List<Progress>>> = _progressList
    
    private val _deleteState = MutableLiveData<Resource<Unit>>()
    val deleteState: LiveData<Resource<Unit>> = _deleteState
    
    private var currentUserId: Long = 0
    
    init {
        viewModelScope.launch {
            userPreferences.userSession.collect { session ->
                currentUserId = session.userId
                if (session.isLoggedIn) {
                    loadProgress()
                }
            }
        }
    }
    
    fun loadProgress() {
        if (currentUserId == 0L) return
        getAllProgressUseCase(currentUserId).onEach { result ->
            _progressList.value = result
        }.launchIn(viewModelScope)
    }
    
    fun deleteProgress(progressId: Long) {
        deleteProgressUseCase(progressId).onEach { result ->
            _deleteState.value = result
            if (result is Resource.Success) {
                loadProgress() // Reload after delete
            }
        }.launchIn(viewModelScope)
    }
    
    fun getCompletedCount(): Int {
        return (_progressList.value as? Resource.Success)?.data
            ?.count { it.isCompleted } ?: 0
    }
    
    fun getTotalProgress(): Int {
        return (_progressList.value as? Resource.Success)?.data?.size ?: 0
    }
    
    fun getAverageScore(): Int {
        val progressData = (_progressList.value as? Resource.Success)?.data
        if (progressData.isNullOrEmpty()) return 0
        
        val totalScore = progressData.sumOf { it.score }
        return totalScore / progressData.size
    }
}