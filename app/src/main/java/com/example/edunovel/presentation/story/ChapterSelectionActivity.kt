package com.yourname.edunovel.presentation.story

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourname.edunovel.databinding.ActivityChapterSelectionBinding
import com.yourname.edunovel.util.Constants

class ChapterSelectionActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityChapterSelectionBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChapterSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupSubjectSpinner()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Select Chapter"
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
    
    private fun setupListeners() {
        // Chapter 1
        binding.cardChapter1.setOnClickListener {
            startStory(1, "Pythagoras Theorem")
        }
        
        // Chapter 2
        binding.cardChapter2.setOnClickListener {
            startStory(2, "Quadratic Equations")
        }
        
        // Chapter 3
        binding.cardChapter3.setOnClickListener {
            startStory(3, "Trigonometry Basics")
        }
    }
    
    private fun startStory(chapterId: Int, chapterTitle: String) {
        val subject = binding.spinnerSubject.selectedItem.toString()
        
        val intent = Intent(this, StoryModeActivity::class.java)
        intent.putExtra("CHAPTER_ID", chapterId)
        intent.putExtra("SUBJECT", subject)
        intent.putExtra("CHAPTER_TITLE", chapterTitle)
        startActivity(intent)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}