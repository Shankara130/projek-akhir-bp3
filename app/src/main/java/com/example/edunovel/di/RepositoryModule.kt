package com.example.edunovel.di

import com.example.edunovel.data.repository.*
import com.example.edunovel.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<ProgressRepository> { ProgressRepositoryImpl(get()) }
    single<MaterialRepository> { MaterialRepositoryImpl(get()) }
    single<QuizRepository> { QuizRepositoryImpl(get(), get()) }
}