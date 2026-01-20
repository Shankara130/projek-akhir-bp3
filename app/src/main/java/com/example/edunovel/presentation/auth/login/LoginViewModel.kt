package com.example.edunovel.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.domain.model.User
import com.example.edunovel.domain.usecase.auth.LoginUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    private val _loginState = MutableLiveData<Resource<User>>()
    val loginState: LiveData<Resource<User>> = _loginState
    
    fun login(username: String, password: String) {
        loginUseCase(username, password).onEach { result ->
            _loginState.value = result
            
            if (result is Resource.Success) {
                result.data?.let { user ->
                    // Save user session
                    viewModelScope.launch {
                        userPreferences.saveUserSession(user.id, user.username)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}