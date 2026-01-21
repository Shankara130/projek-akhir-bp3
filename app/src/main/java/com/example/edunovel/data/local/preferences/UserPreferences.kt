package com.yourname.edunovel.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(context: Context) {
    
    private val dataStore = context.dataStore
    
    private object PreferencesKeys {
        val USER_ID = intPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }
    
    suspend fun saveUserSession(userId: Int, username: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.USERNAME] = username
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
        }
    }
    
    suspend fun clearUserSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    val userSession: Flow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserSession(
                userId = preferences[PreferencesKeys.USER_ID] ?: 0,
                username = preferences[PreferencesKeys.USERNAME] ?: "",
                isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
            )
        }
    
    data class UserSession(
        val userId: Int,
        val username: String,
        val isLoggedIn: Boolean
    )
}