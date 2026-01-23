package com.example.edunovel.domain.usecase.auth

import com.example.edunovel.domain.model.User
import com.example.edunovel.domain.repository.UserRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(username: String, email: String, password: String): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading)
            
            // Validation
            if (username.isBlank()) {
                emit(Resource.Error("Username cannot be empty"))
                return@flow
            }
            
            if (username.length < 3) {
                emit(Resource.Error("Username must be at least 3 characters"))
                return@flow
            }
            
            if (email.isBlank()) {
                emit(Resource.Error("Email cannot be empty"))
                return@flow
            }
            
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emit(Resource.Error("Please enter a valid email"))
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
            
            // Check if username already exists
            val existingUser = userRepository.getUserByUsername(username)
            if (existingUser != null) {
                emit(Resource.Error("Username already exists"))
                return@flow
            }
            
            // Create new user
            val newUser = User(
                username = username,
                email = email,
                password = password
            )
            
            val userId = userRepository.insertUser(newUser)
            emit(Resource.Success(userId))
            
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Registration failed"))
        }
    }
}