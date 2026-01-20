package com.example.edunovel.presentation.progress

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProgressBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        
        // TODO: Implement Progress
        Toast.makeText(this, "My Progress - Coming Soon!", Toast.LENGTH_SHORT).show()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "My Progress"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}