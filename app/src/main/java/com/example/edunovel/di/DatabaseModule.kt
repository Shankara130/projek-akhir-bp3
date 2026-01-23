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
        AppDatabase.getInstance(androidContext())
    }
    
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().characterDao() }
    single { get<AppDatabase>().progressDao() }
    single { get<AppDatabase>().quizDao() }
    single { get<AppDatabase>().quizQuestionDao() }
    single { get<AppDatabase>().materialDao() }
}