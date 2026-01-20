package com.example.edunovel.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.R
import com.example.edunovel.databinding.ActivityMainBinding
import com.example.edunovel.presentation.about.AboutActivity
import com.example.edunovel.presentation.auth.login.LoginActivity
import com.example.edunovel.presentation.character.CharacterListActivity
import com.example.edunovel.presentation.highscore.HighScoreActivity
import com.example.edunovel.presentation.progress.ProgressActivity
import com.example.edunovel.presentation.quiz.QuizActivity
import com.example.edunovel.presentation.story.StoryModeActivity
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupObservers()
        setupMenuCards()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "EduNovel"
        }
    }
    
    private fun setupObservers() {
        viewModel.userSession.observe(this) { session ->
            binding.tvWelcome.text = "Welcome, ${session.username}!"
        }
        
        viewModel.logoutState.observe(this) { result ->
            when (result) {
                is Resource.Success -> {
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is Resource.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    
    private fun setupMenuCards() {
        // Story Mode
        binding.cardStoryMode.setOnClickListener {
            navigateToStoryMode()
        }
        
        // Quiz Challenge
        binding.cardQuiz.setOnClickListener {
            navigateToQuiz()
        }
        
        // My Characters
        binding.cardCharacters.setOnClickListener {
            navigateToCharacters()
        }
        
        // My Progress
        binding.cardProgress.setOnClickListener {
            navigateToProgress()
        }
        
        // High Scores
        binding.cardHighScore.setOnClickListener {
            navigateToHighScore()
        }
        
        // About
        binding.cardAbout.setOnClickListener {
            navigateToAbout()
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun navigateToStoryMode() {
        val intent = Intent(this, StoryModeActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToQuiz() {
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToCharacters() {
        val intent = Intent(this, CharacterListActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToProgress() {
        val intent = Intent(this, ProgressActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToHighScore() {
        val intent = Intent(this, HighScoreActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Exit") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}