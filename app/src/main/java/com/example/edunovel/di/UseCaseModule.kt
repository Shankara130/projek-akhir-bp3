package com.example.edunovel.di

import com.example.edunovel.domain.usecase.auth.*
import com.example.edunovel.domain.usecase.character.*
import com.example.edunovel.domain.usecase.progress.*
import com.example.edunovel.domain.usecase.story.*
import com.example.edunovel.domain.usecase.quiz.*
import org.koin.dsl.module

val useCaseModule = module {
    // Auth Use Cases
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    
    // Character Use Cases
    factory { CreateCharacterUseCase(get()) }
    factory { GetUserCharactersUseCase(get()) }
    factory { UpdateCharacterUseCase(get()) }
    factory { DeleteCharacterUseCase(get()) }
    
    // Progress Use Cases
    factory { SaveProgressUseCase(get()) }
    
    // Story Use Cases
    factory { GetStoryContentUseCase(get(), get()) }
    factory { GetChaptersUseCase(get(), get()) }
    
    // Quiz Use Cases
    factory { GetQuizQuestionsUseCase(get()) }
    factory { SaveQuizResultUseCase(get()) }
    factory { GetQuizHistoryUseCase(get()) }
}