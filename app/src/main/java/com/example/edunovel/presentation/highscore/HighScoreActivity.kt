package com.example.edunovel.presentation.highscore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityHighscoreBinding

class HighScoreActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHighscoreBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        
        // TODO: Implement High Score
        Toast.makeText(this, "High Scores - Coming Soon!", Toast.LENGTH_SHORT).show()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "High Scores"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}