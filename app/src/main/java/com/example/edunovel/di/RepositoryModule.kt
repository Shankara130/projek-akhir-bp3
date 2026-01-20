package com.example.edunovel.di

import com.example.edunovel.data.repository.CharacterRepositoryImpl
import com.example.edunovel.data.repository.UserRepositoryImpl
import com.example.edunovel.domain.repository.CharacterRepository
import com.example.edunovel.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
}