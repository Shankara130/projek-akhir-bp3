package com.example.edunovel.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.databinding.ActivitySplashBinding
import com.example.edunovel.presentation.auth.login.LoginActivity
import com.example.edunovel.presentation.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    private val userPreferences: UserPreferences by inject()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        checkUserSession()
    }
    
    private fun checkUserSession() {
        lifecycleScope.launch {
            delay(2000) // Show splash for 2 seconds
            
            val session = userPreferences.userSession.first()
            
            if (session.isLoggedIn) {
                // User is logged in, go to main activity
                navigateToMain()
            } else {
                // User not logged in, go to login
                navigateToLogin()
            }
        }
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}