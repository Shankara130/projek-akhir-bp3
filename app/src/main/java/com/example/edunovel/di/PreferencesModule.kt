package com.example.edunovel.di

import com.example.edunovel.data.local.preferences.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single { UserPreferences(androidContext()) }
}