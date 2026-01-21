package com.example.edunovel.di

import androidx.room.Room
import com.example.edunovel.data.local.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { CoroutineScope(SupervisorJob()) }
    
    single {
        AppDatabase.getDatabase(androidContext(), get())
    }
    
    single { get().userDao() }
    single { get().characterDao() }
    single { get().progressDao() }
    single { get().quizDao() }
    single { get().materialDao() }
}