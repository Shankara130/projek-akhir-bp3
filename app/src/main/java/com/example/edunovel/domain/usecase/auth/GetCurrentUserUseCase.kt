package com.example.edunovel.domain.usecase.auth

import com.example.edunovel.data.local.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCurrentUserUseCase(
    private val userPreferences: UserPreferences
) {
    operator fun invoke(): Flow<UserPreferences.UserSession> {
        return userPreferences.userSession
    }
}