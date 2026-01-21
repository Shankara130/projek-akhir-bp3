package com.example.edunovel.presentation.progress

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edunovel.databinding.ActivityProgressBinding
import com.example.edunovel.presentation.progress.adapter.ProgressAdapter
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProgressBinding
    private val viewModel: ProgressViewModel by viewModel()
    private lateinit var progressAdapter: ProgressAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupObservers()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Progres Belajar Saya"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupRecyclerView() {
        progressAdapter = ProgressAdapter(
            onDeleteClick = { progress ->
                showDeleteConfirmation(progress.id, progress.subject)
            }
        )
        
        binding.rvProgress.apply {
            layoutManager = LinearLayoutManager(this@ProgressActivity)
            adapter = progressAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.progressList.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvProgress.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    
                    result.data?.let { progressList ->
                        if (progressList.isEmpty()) {
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.rvProgress.visibility = View.GONE
                            binding.statsCard.visibility = View.GONE
                        } else {
                            binding.tvEmpty.visibility = View.GONE
                            binding.rvProgress.visibility = View.VISIBLE
                            binding.statsCard.visibility = View.VISIBLE
                            progressAdapter.submitList(progressList)
                            updateStats()
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        viewModel.deleteState.observe(this) { result ->
            when (result) {
                is Resource.Success -> {
                    Toast.makeText(this, "Progres dihapus", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    
    private fun updateStats() {
        binding.apply {
            tvTotalProgress.text = viewModel.getTotalProgress().toString()
            tvCompletedCount.text = viewModel.getCompletedCount().toString()
            tvAverageScore.text = "${viewModel.getAverageScore()}%"
        }
    }
    
    private fun showDeleteConfirmation(progressId: Int, subject: String) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Progres")
            .setMessage("Hapus progres untuk $subject?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.deleteProgress(progressId)
            }
            .setNegativeButton("Batal", null)
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}