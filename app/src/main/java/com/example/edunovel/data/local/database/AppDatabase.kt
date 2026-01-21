package com.yourname.edunovel.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yourname.edunovel.data.local.database.converters.StringListConverter
import com.yourname.edunovel.data.local.database.dao.*
import com.yourname.edunovel.data.local.database.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        CharacterEntity::class,
        ProgressEntity::class,
        QuizEntity::class,
        MaterialEntity::class,
        QuizQuestionEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun characterDao(): CharacterDao

    abstract fun progressDao(): ProgressDao

    abstract fun quizDao(): QuizDao

    abstract fun materialDao(): MaterialDao

    abstract fun quizQuestionDao(): QuizQuestionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "edunovel_database",
                        ).addCallback(DatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }

        private class DatabaseCallback(
            private val scope: CoroutineScope,
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }
        }

        suspend fun populateDatabase(database: AppDatabase) {
            val characterDao = database.characterDao()
            val materialDao = database.materialDao()
            val quizQuestionDao = database.quizQuestionDao()

            // ═══════════════════════════════════════════════════════════════
            // PRE-POPULATE DEFAULT CHARACTERS
            // ═══════════════════════════════════════════════════════════════
            val defaultCharacters =
                listOf(
                    CharacterEntity(
                        userId = 0,
                        name = "Luna",
                        imageUri = null,
                        personality = "Cheerful",
                        subject = "Math",
                        description = "A cheerful math teacher who makes learning fun and easy to understand",
                        isDefault = true,
                    ),
                    CharacterEntity(
                        userId = 0,
                        name = "Prof. Alex",
                        imageUri = null,
                        personality = "Serious",
                        subject = "Science",
                        description = "A brilliant science professor with deep knowledge",
                        isDefault = true,
                    ),
                    CharacterEntity(
                        userId = 0,
                        name = "Miss Emma",
                        imageUri = null,
                        personality = "Friendly",
                        subject = "English",
                        description = "A friendly English teacher who loves literature",
                        isDefault = true,
                    ),
                    CharacterEntity(
                        userId = 0,
                        name = "Mr. Kevin",
                        imageUri = null,
                        personality = "Energetic",
                        subject = "Physics",
                        description = "An energetic physics teacher with practical demonstrations",
                        isDefault = true,
                    ),
                    CharacterEntity(
                        userId = 0,
                        name = "Dr. Sarah",
                        imageUri = null,
                        personality = "Wise",
                        subject = "Chemistry",
                        description = "A wise chemistry expert who explains complex concepts simply",
                        isDefault = true,
                    ),
                )

            defaultCharacters.forEach {
                characterDao.insertCharacter(it)
            }

            // ═══════════════════════════════════════════════════════════════
            // PRE-POPULATE LEARNING MATERIALS
            // ═══════════════════════════════════════════════════════════════
            val quizQuestions =
                listOf(
                    // Matematika - Pythagoras
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 1,
                        question = "Dalam segitiga siku-siku, jika a = 3 dan b = 4, berapakah c (sisi miring)?",
                        options = listOf("5", "6", "7", "8"),
                        correctAnswer = 0,
                        explanation = "Menggunakan teorema Pythagoras: c² = a² + b² = 9 + 16 = 25, jadi c = 5",
                        difficulty = "Mudah",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 1,
                        question = "Teorema Pythagoras berlaku untuk jenis segitiga apa?",
                        options = listOf("Sama sisi", "Siku-siku", "Sama kaki", "Sembarang"),
                        correctAnswer = 1,
                        explanation = "Teorema Pythagoras hanya berlaku untuk segitiga siku-siku",
                        difficulty = "Mudah",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 1,
                        question = "Jika kaki segitiga siku-siku adalah 5 dan 12, berapakah sisi miringnya?",
                        options = listOf("13", "14", "15", "17"),
                        correctAnswer = 0,
                        explanation = "c² = 5² + 12² = 25 + 144 = 169, jadi c = 13",
                        difficulty = "Sedang",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 1,
                        question = "Dalam rumus a² + b² = c², apa yang dimaksud dengan c?",
                        options = listOf("Sisi samping", "Sisi depan", "Sisi miring", "Sisi sembarang"),
                        correctAnswer = 2,
                        explanation = "c selalu mewakili sisi miring (sisi terpanjang) dalam segitiga siku-siku",
                        difficulty = "Mudah",
                    ),
                    // Matematika - Persamaan Kuadrat
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 2,
                        question = "Berapa solusi dari x² - 5x + 6 = 0?",
                        options = listOf("x = 2 atau 3", "x = 1 atau 6", "x = -2 atau -3", "x = 0 atau 5"),
                        correctAnswer = 0,
                        explanation = "Dengan memfaktorkan: (x-2)(x-3) = 0, jadi x = 2 atau x = 3",
                        difficulty = "Sedang",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 2,
                        question = "Dalam rumus kuadrat, 'b² - 4ac' disebut apa?",
                        options = listOf("Titik puncak", "Diskriminan", "Akar", "Koefisien"),
                        correctAnswer = 1,
                        explanation = "b² - 4ac disebut diskriminan dan menentukan sifat akar-akar persamaan",
                        difficulty = "Sedang",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 2,
                        question = "Bentuk umum persamaan kuadrat adalah:",
                        options = listOf("ax + b = 0", "ax² + bx + c = 0", "ax³ + bx² + c = 0", "a + bx + c = 0"),
                        correctAnswer = 1,
                        explanation = "Bentuk standar adalah ax² + bx + c = 0 dimana a ≠ 0",
                        difficulty = "Mudah",
                    ),
                    // Matematika - Trigonometri
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 3,
                        question = "Berapa nilai sin(30°)?",
                        options = listOf("0,5", "0,707", "0,866", "1"),
                        correctAnswer = 0,
                        explanation = "sin(30°) = 1/2 = 0,5",
                        difficulty = "Mudah",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 3,
                        question = "Dalam rumus SOH CAH TOA, 'TOA' mewakili apa?",
                        options = listOf("Rasio sinus", "Rasio kosinus", "Rasio tangen", "Semua rasio"),
                        correctAnswer = 2,
                        explanation = "TOA berarti Tangen = Depan / Samping",
                        difficulty = "Mudah",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 3,
                        question = "Berapa nilai cos(0°)?",
                        options = listOf("0", "0,5", "0,866", "1"),
                        correctAnswer = 3,
                        explanation = "cos(0°) = 1",
                        difficulty = "Mudah",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 3,
                        question = "Jika sin(θ) = depan/miring, maka cos(θ) = ?",
                        options = listOf("depan/samping", "samping/miring", "miring/depan", "samping/depan"),
                        correctAnswer = 1,
                        explanation = "Kosinus didefinisikan sebagai sisi samping dibagi sisi miring",
                        difficulty = "Sedang",
                    ),
                    // Soal Tambahan
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 1,
                        question = "Sebuah tangga sepanjang 10 meter bersandar pada dinding. Jarak kaki tangga dari dinding 6 meter. Berapa tinggi tangga di dinding?",
                        options = listOf("6 meter", "8 meter", "10 meter", "4 meter"),
                        correctAnswer = 1,
                        explanation = "Menggunakan Pythagoras: h² = 10² - 6² = 100 - 36 = 64, jadi h = 8 meter",
                        difficulty = "Sedang",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 2,
                        question = "Berapa banyak solusi yang biasanya dimiliki persamaan kuadrat?",
                        options = listOf("Selalu 1", "Selalu 2", "0, 1, atau 2", "Tak terhingga"),
                        correctAnswer = 2,
                        explanation = "Persamaan kuadrat dapat memiliki 0, 1, atau 2 solusi real tergantung diskriminan",
                        difficulty = "Sedang",
                    ),
                    QuizQuestionEntity(
                        subject = "Matematika",
                        chapterId = 3,
                        question = "Berapa nilai tan(45°)?",
                        options = listOf("0", "0,5", "1", "√3"),
                        correctAnswer = 2,
                        explanation = "tan(45°) = 1 karena sisi depan dan samping sama panjang",
                        difficulty = "Mudah",
                    ),
                )

            quizQuestionDao.insertQuestions(quizQuestions)
        }
    }
}
