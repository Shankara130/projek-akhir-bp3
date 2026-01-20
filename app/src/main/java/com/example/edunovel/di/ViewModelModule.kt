package com.example.edunovel.di

import com.example.edunovel.presentation.auth.login.LoginViewModel
import com.example.edunovel.presentation.auth.register.RegisterViewModel
import com.example.edunovel.presentation.character.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Auth ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    
    // Character ViewModels
    viewModel { CharacterViewModel(get(), get(), get(), get(), get()) }
}