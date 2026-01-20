package com.example.edunovel.domain.usecase.auth

import com.example.edunovel.domain.model.User
import com.example.edunovel.domain.repository.UserRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(username: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            
            // Validation
            if (username.isBlank()) {
                emit(Resource.Error("Username cannot be empty"))
                return@flow
            }
            
            if (password.isBlank()) {
                emit(Resource.Error("Password cannot be empty"))
                return@flow
            }
            
            if (password.length < 6) {
                emit(Resource.Error("Password must be at least 6 characters"))
                return@flow
            }
            
            // Attempt login
            val user = userRepository.login(username, password)
            
            if (user != null) {
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("Invalid username or password"))
            }
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Login failed"))
        }
    }
}