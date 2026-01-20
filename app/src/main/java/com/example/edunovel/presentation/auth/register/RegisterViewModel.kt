package com.example.edunovel.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.domain.usecase.auth.RegisterUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    
    private val _registerState = MutableLiveData<Resource<Long>>()
    val registerState: LiveData<Resource<Long>> = _registerState
    
    fun register(username: String, email: String, password: String, confirmPassword: String) {
        // Validate password match
        if (password != confirmPassword) {
            _registerState.value = Resource.Error("Passwords do not match")
            return
        }
        
        registerUseCase(username, email, password).onEach { result ->
            _registerState.value = result
        }.launchIn(viewModelScope)
    }
}