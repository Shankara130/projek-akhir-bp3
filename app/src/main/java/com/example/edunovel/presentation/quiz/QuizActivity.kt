package com.example.edunovel.presentation.quiz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityQuizBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        
        // TODO: Implement Quiz
        Toast.makeText(this, "Quiz Challenge - Coming Soon!", Toast.LENGTH_SHORT).show()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Quiz Challenge"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}