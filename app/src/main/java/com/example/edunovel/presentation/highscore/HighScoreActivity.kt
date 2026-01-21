package com.example.edunovel.presentation.highscore

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edunovel.databinding.ActivityHighscoreBinding
import com.example.edunovel.presentation.highscore.adapter.HighScoreAdapter
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HighScoreActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHighscoreBinding
    private val viewModel: HighScoreViewModel by viewModel()
    private lateinit var highScoreAdapter: HighScoreAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupObservers()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Papan Peringkat"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupRecyclerView() {
        highScoreAdapter = HighScoreAdapter()
        
        binding.rvHighScores.apply {
            layoutManager = LinearLayoutManager(this@HighScoreActivity)
            adapter = highScoreAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.highScores.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvHighScores.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    
                    result.data?.let { scores ->
                        if (scores.isEmpty()) {
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.rvHighScores.visibility = View.GONE
                        } else {
                            binding.tvEmpty.visibility = View.GONE
                            binding.rvHighScores.visibility = View.VISIBLE
                            highScoreAdapter.submitList(scores)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}