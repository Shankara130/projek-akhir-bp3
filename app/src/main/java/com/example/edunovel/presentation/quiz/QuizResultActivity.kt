package com.example.edunovel.presentation.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityQuizResultBinding
import com.example.edunovel.domain.model.QuizSession
import com.example.edunovel.presentation.main.MainActivity

class QuizResultActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityQuizResultBinding
    private lateinit var quizSession: QuizSession
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        quizSession = intent.getParcelableExtra("QUIZ_SESSION") ?: run {
            finish()
            return
        }
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        val score = quizSession.calculateScore()
        val correct = quizSession.getCorrectCount()
        val total = quizSession.getTotalQuestions()
        
        binding.apply {
            tvScore.text = "$score%"
            tvCorrect.text = "$correct"
            tvIncorrect.text = "${total - correct}"
            tvTotal.text = "$total"
            
            progressCircle.progress = score
            
            // Set result message and color based on score
            when {
                score >= 80 -> {
                    tvResultMessage.text = "Excellent!"
                    tvResultMessage.setTextColor(getColor(android.R.color.holo_green_dark))
                }
                score >= 60 -> {
                    tvResultMessage.text = "Good Job!"
                    tvResultMessage.setTextColor(getColor(android.R.color.holo_orange_dark))
                }
                else -> {
                    tvResultMessage.text = "Keep Practicing!"
                    tvResultMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                }
            }
        }
    }
    
    private fun setupListeners() {
        binding.btnReview.setOnClickListener {
            // TODO: Navigate to review answers
            navigateToHome()
        }
        
        binding.btnHome.setOnClickListener {
            navigateToHome()
        }
        
        binding.btnRetry.setOnClickListener {
            // Start new quiz
            val intent = Intent(this, QuizSelectionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
    
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
    
    override fun onBackPressed() {
        navigateToHome()
    }
}