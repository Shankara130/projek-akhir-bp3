package com.example.edunovel.domain.usecase.auth

import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogoutUseCase(
    private val userPreferences: UserPreferences
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            userPreferences.clearUserSession()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Logout failed"))
        }
    }
}