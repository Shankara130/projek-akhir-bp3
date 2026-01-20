package com.example.edunovel.di

import com.example.edunovel.domain.usecase.auth.*
import com.example.edunovel.domain.usecase.character.*
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
}