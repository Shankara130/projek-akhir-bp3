package com.example.edunovel.presentation.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityQuizSelectionBinding
import com.example.edunovel.util.Constants

class QuizSelectionActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityQuizSelectionBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupSubjectSpinner()
        setupQuestionCountSpinner()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Quiz Challenge"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupSubjectSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Constants.SUBJECTS
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSubject.adapter = adapter
    }
    
    private fun setupQuestionCountSpinner() {
        val counts = listOf("5 Questions", "10 Questions", "15 Questions", "20 Questions")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            counts
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerQuestionCount.adapter = adapter
        binding.spinnerQuestionCount.setSelection(1) // Default to 10
    }
    
    private fun setupListeners() {
        binding.btnStartQuiz.setOnClickListener {
            val subject = binding.spinnerSubject.selectedItem.toString()
            val questionCount = when (binding.spinnerQuestionCount.selectedItemPosition) {
                0 -> 5
                1 -> 10
                2 -> 15
                3 -> 20
                else -> 10
            }
            
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("SUBJECT", subject)
            intent.putExtra("QUESTION_COUNT", questionCount)
            startActivity(intent)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}