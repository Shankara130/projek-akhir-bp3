package com.example.edunovel.presentation.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.R
import com.example.edunovel.databinding.ActivityQuizBinding
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizViewModel by viewModel()
    
    private var subject: String = "Math"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        subject = intent.getStringExtra("SUBJECT") ?: "Math"
        
        setupToolbar()
        setupObservers()
        setupListeners()
        
        // Load quiz
        viewModel.loadQuiz(subject, 10)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "$subject Quiz"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupObservers() {
        viewModel.quizState.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.contentLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                    updateUI()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
        
        viewModel.currentQuestionIndex.observe(this) { 
            updateUI()
        }
        
        viewModel.saveResultState.observe(this) { result ->
            when (result) {
                is Resource.Success -> {
                    navigateToResult()
                }
                is Resource.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    
    private fun setupListeners() {
        binding.rgOptions.setOnCheckedChangeListener { _, checkedId ->
            val answerIndex = when (checkedId) {
                R.id.rbOption1 -> 0
                R.id.rbOption2 -> 1
                R.id.rbOption3 -> 2
                R.id.rbOption4 -> 3
                else -> -1
            }
            if (answerIndex != -1) {
                viewModel.answerQuestion(answerIndex)
            }
        }
        
        binding.btnNext.setOnClickListener {
            val questions = (viewModel.quizState.value as? Resource.Success)?.data ?: return@setOnClickListener
            val currentIndex = viewModel.currentQuestionIndex.value ?: 0
            
            if (currentIndex == questions.size - 1) {
                showFinishConfirmation()
            } else {
                viewModel.nextQuestion()
            }
        }
        
        binding.btnPrevious.setOnClickListener {
            viewModel.previousQuestion()
        }
    }
    
    private fun updateUI() {
        val questions = (viewModel.quizState.value as? Resource.Success)?.data ?: return
        val currentIndex = viewModel.currentQuestionIndex.value ?: 0
        val question = questions.getOrNull(currentIndex) ?: return
        
        // Update question number
        binding.tvQuestionNumber.text = "Question ${currentIndex + 1} of ${questions.size}"
        
        // Update progress
        val progress = ((currentIndex + 1) * 100) / questions.size
        binding.progressIndicator.progress = progress
        
        // Update question text
        binding.tvQuestion.text = question.question
        
        // Update options
        binding.rbOption1.text = question.options.getOrNull(0) ?: ""
        binding.rbOption2.text = question.options.getOrNull(1) ?: ""
        binding.rbOption3.text = question.options.getOrNull(2) ?: ""
        binding.rbOption4.text = question.options.getOrNull(3) ?: ""
        
        // Restore selected answer
        binding.rgOptions.clearCheck()
        val currentAnswer = viewModel.getCurrentAnswer()
        if (currentAnswer != null) {
            when (currentAnswer) {
                0 -> binding.rbOption1.isChecked = true
                1 -> binding.rbOption2.isChecked = true
                2 -> binding.rbOption3.isChecked = true
                3 -> binding.rbOption4.isChecked = true
            }
        }
        
        // Update navigation buttons
        binding.btnPrevious.apply {
            isEnabled = currentIndex > 0
            alpha = if (isEnabled) 1.0f else 0.5f
        }
        
        binding.btnNext.text = if (currentIndex == questions.size - 1) {
            "Finish"
        } else {
            "Next"
        }
    }
    
    private fun showFinishConfirmation() {
        // Logic simplified: just show confirmation if any question is not answered
        // For simplicity, we can let user finish anytime
        AlertDialog.Builder(this)
            .setTitle("Finish Quiz?")
            .setMessage("Do you want to submit your answers?")
            .setPositiveButton("Finish") { _, _ ->
                viewModel.finishQuiz()
            }
            .setNegativeButton("Continue", null)
            .show()
    }
    
    private fun navigateToResult() {
        val session = viewModel.finishedQuizSession.value ?: return
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("QUIZ_SESSION", session)
        startActivity(intent)
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit Quiz?")
            .setMessage("Your progress will be lost. Are you sure?")
            .setPositiveButton("Exit") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("Continue", null)
            .show()
    }
}