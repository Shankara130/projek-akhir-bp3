package com.example.edunovel.di

import com.example.edunovel.presentation.auth.login.LoginViewModel
import com.example.edunovel.presentation.auth.register.RegisterViewModel
import com.example.edunovel.presentation.character.CharacterViewModel
import com.example.edunovel.presentation.main.MainViewModel
import com.example.edunovel.presentation.story.StoryViewModel
import com.example.edunovel.presentation.quiz.QuizViewModel
import com.example.edunovel.presentation.progress.ProgressViewModel
import com.example.edunovel.presentation.highscore.HighScoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Auth ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    
    // Main ViewModel
    viewModel { MainViewModel(get(), get()) }
    
    // Character ViewModels
    viewModel { CharacterViewModel(get(), get(), get(), get(), get()) }
    
    // Story ViewModel
    viewModel { StoryViewModel(get(), get(), get(), get()) }
    
    // Quiz ViewModel
    viewModel { QuizViewModel(get(), get(), get()) }
    
    // Progress ViewModel
    viewModel { ProgressViewModel(get(), get(), get()) }
    
    // HighScore ViewModel
    viewModel { HighScoreViewModel(get()) }
}