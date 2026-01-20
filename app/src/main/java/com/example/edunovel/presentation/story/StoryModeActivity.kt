package com.example.edunovel.presentation.story

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.edunovel.R
import com.example.edunovel.databinding.ActivityStoryModeBinding
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryModeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityStoryModeBinding
    private val viewModel: StoryViewModel by viewModel()
    
    private var chapterId: Int = 1 // Default to chapter 1
    private var subject: String = "Math" // Default subject
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get chapter info from intent
        chapterId = intent.getIntExtra("CHAPTER_ID", 1)
        subject = intent.getStringExtra("SUBJECT") ?: "Math"
        
        setupToolbar()
        setupObservers()
        setupListeners()
        
        // Load story content
        viewModel.loadStory(chapterId)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Story Mode"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupObservers() {
        viewModel.storyContent.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.contentLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                    updateSlideContent()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        
        viewModel.currentSlide.observe(this) { position ->
            updateSlideContent()
            updateNavigationButtons()
            updateProgressIndicator()
            
            // Auto-save progress
            viewModel.saveProgress(chapterId, subject, position)
        }
        
        viewModel.character.observe(this) { character ->
            character?.let {
                binding.tvCharacterName.text = it.name
                
                // Load character image
                if (it.imageUri != null) {
                    Glide.with(this)
                        .load(it.imageUri)
                        .placeholder(R.drawable.ic_character_placeholder)
                        .into(binding.ivCharacter)
                } else {
                    binding.ivCharacter.setImageResource(R.drawable.ic_character_placeholder)
                }
            }
        }
    }
    
    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            val material = viewModel.getCurrentMaterial()
            
            if (material?.hasQuiz == true) {
                // Show quiz before proceeding
                showQuiz()
            } else {
                viewModel.nextSlide()
            }
        }
        
        binding.btnPrevious.setOnClickListener {
            viewModel.previousSlide()
        }
        
        binding.cardDialogue.setOnClickListener {
            viewModel.nextSlide()
        }
    }
    
    private fun updateSlideContent() {
        val material = viewModel.getCurrentMaterial() ?: return
        
        binding.apply {
            tvDialogue.text = material.dialogueText
            tvContent.text = material.content
            tvTitle.text = material.title
        }
    }
    
    private fun updateNavigationButtons() {
        binding.btnPrevious.apply {
            isEnabled = viewModel.hasPrevious()
            alpha = if (isEnabled) 1.0f else 0.5f
        }
        
        binding.btnNext.apply {
            isEnabled = viewModel.hasNext()
            text = if (viewModel.hasNext()) "Next" else "Finish"
        }
    }
    
    private fun updateProgressIndicator() {
        val current = viewModel.currentSlide.value ?: 0
        viewModel.storyContent.value?.data?.let { materials ->
            val total = materials.size
            val progress = ((current + 1) * 100) / total
            
            binding.progressIndicator.progress = progress
            binding.tvProgress.text = "${current + 1} / $total"
        }
    }
    
    private fun showQuiz() {
        // TODO: Implement quiz dialog
        AlertDialog.Builder(this)
            .setTitle("Quiz Time!")
            .setMessage("Answer the quiz question to continue.")
            .setPositiveButton("Start Quiz") { _, _ ->
                // TODO: Open quiz activity
                viewModel.nextSlide()
            }
            .setNegativeButton("Skip") { _, _ ->
                viewModel.nextSlide()
            }
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit Story")
            .setMessage("Your progress has been saved. Exit story mode?")
            .setPositiveButton("Exit") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("Continue", null)
            .show()
    }
}