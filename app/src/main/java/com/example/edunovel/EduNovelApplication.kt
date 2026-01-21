package com.example.edunovel

import android.app.Application
import com.example.edunovel.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EduNovelApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Start Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@EduNovelApplication)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    preferencesModule
                )
            )
        }
    }
}