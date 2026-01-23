package com.example.edunovel.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.edunovel.data.local.database.converters.StringListConverter
import com.example.edunovel.data.local.database.dao.*
import com.example.edunovel.data.local.database.entity.*

@Database(
    entities = [
        UserEntity::class,
        CharacterEntity::class,
        ProgressEntity::class,
        QuizEntity::class,
        QuizQuestionEntity::class,
        MaterialEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun characterDao(): CharacterDao
    abstract fun progressDao(): ProgressDao
    abstract fun quizDao(): QuizDao
    abstract fun quizQuestionDao(): QuizQuestionDao
    abstract fun materialDao(): MaterialDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "edunovel_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}