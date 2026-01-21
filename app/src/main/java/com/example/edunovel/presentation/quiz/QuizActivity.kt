package com.example.edunovel.presentation.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
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
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
        
        viewModel.quizSession.observe(this) { session ->
            updateUI(session)
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
            when (checkedId) {
                R.id.rbOption1 -> viewModel.answerQuestion(0)
                R.id.rbOption2 -> viewModel.answerQuestion(1)
                R.id.rbOption3 -> viewModel.answerQuestion(2)
                R.id.rbOption4 -> viewModel.answerQuestion(3)
            }
        }
        
        binding.btnNext.setOnClickListener {
            val session = viewModel.quizSession.value
            if (session != null && session.currentQuestionIndex == session.getTotalQuestions() - 1) {
                showFinishConfirmation()
            } else {
                viewModel.nextQuestion()
            }
        }
        
        binding.btnPrevious.setOnClickListener {
            viewModel.previousQuestion()
        }
    }
    
    private fun updateUI(session: com.example.edunovel.domain.model.QuizSession) {
        val question = session.getCurrentQuestion() ?: return
        
        // Update question number
        binding.tvQuestionNumber.text = "Question ${session.currentQuestionIndex + 1} of ${session.getTotalQuestions()}"
        
        // Update progress
        val progress = ((session.currentQuestionIndex + 1) * 100) / session.getTotalQuestions()
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
            isEnabled = session.currentQuestionIndex > 0
            alpha = if (isEnabled) 1.0f else 0.5f
        }
        
        binding.btnNext.text = if (session.currentQuestionIndex == session.getTotalQuestions() - 1) {
            "Finish"
        } else {
            "Next"
        }
    }
    
    private fun showFinishConfirmation() {
        val session = viewModel.quizSession.value ?: return
        val answered = session.getAnsweredCount()
        val total = session.getTotalQuestions()
        
        if (answered < total) {
            AlertDialog.Builder(this)
                .setTitle("Finish Quiz?")
                .setMessage("You have answered $answered out of $total questions. Do you want to finish?")
                .setPositiveButton("Finish") { _, _ ->
                    viewModel.finishQuiz()
                }
                .setNegativeButton("Continue", null)
                .show()
        } else {
            viewModel.finishQuiz()
        }
    }
    
    private fun navigateToResult() {
        val session = viewModel.quizSession.value ?: return
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