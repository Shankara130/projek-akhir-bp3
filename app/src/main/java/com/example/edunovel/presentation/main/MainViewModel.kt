package com.example.edunovel.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.domain.usecase.auth.GetCurrentUserUseCase
import com.example.edunovel.domain.usecase.auth.LogoutUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    
    private val _userSession = MutableLiveData<UserPreferences.UserSession>()
    val userSession: LiveData<UserPreferences.UserSession> = _userSession
    
    private val _logoutState = MutableLiveData<Resource<Unit>>()
    val logoutState: LiveData<Resource<Unit>> = _logoutState
    
    init {
        loadUserSession()
    }
    
    private fun loadUserSession() {
        getCurrentUserUseCase().onEach { session ->
            _userSession.value = session
        }.launchIn(viewModelScope)
    }
    
    fun logout() {
        logoutUseCase().onEach { result ->
            _logoutState.value = result
        }.launchIn(viewModelScope)
    }
}