package com.example.edunovel.presentation.story

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityStoryModeBinding

class StoryModeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityStoryModeBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        
        // TODO: Implement Story Mode
        Toast.makeText(this, "Story Mode - Coming Soon!", Toast.LENGTH_SHORT).show()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Story Mode"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}