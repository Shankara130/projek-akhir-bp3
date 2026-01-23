package com.example.edunovel.presentation.character

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edunovel.databinding.ActivityCharacterListBinding
import com.example.edunovel.domain.model.Character
import com.example.edunovel.presentation.character.adapter.CharacterAdapter
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCharacterListBinding
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharacterAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "My Characters"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter(
            onItemClick = { character ->
                navigateToCharacterDetail(character)
            },
            onEditClick = { character ->
                if (!character.isDefault) {
                    navigateToEditCharacter(character)
                } else {
                    Toast.makeText(this, "Cannot edit default characters", Toast.LENGTH_SHORT).show()
                }
            },
            onDeleteClick = { character ->
                if (!character.isDefault) {
                    showDeleteConfirmation(character)
                } else {
                    Toast.makeText(this, "Cannot delete default characters", Toast.LENGTH_SHORT).show()
                }
            }
        )
        
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@CharacterListActivity)
            adapter = characterAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.characters.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvCharacters.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { characters ->
                        if (characters.isEmpty()) {
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.rvCharacters.visibility = View.GONE
                        } else {
                            binding.tvEmpty.visibility = View.GONE
                            binding.rvCharacters.visibility = View.VISIBLE
                            characterAdapter.submitList(characters)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        viewModel.deleteCharacterState.observe(this) { result ->
            when (result) {
                is Resource.Success -> {
                    Toast.makeText(this, "Character deleted", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    
    private fun setupListeners() {
        binding.fabAddCharacter.setOnClickListener {
            navigateToCreateCharacter()
        }
    }
    
    private fun showDeleteConfirmation(character: Character) {
        AlertDialog.Builder(this)
            .setTitle("Delete Character")
            .setMessage("Are you sure you want to delete ${character.name}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteCharacter(character.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun navigateToCreateCharacter() {
        val intent = Intent(this, CreateCharacterActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToEditCharacter(character: Character) {
        val intent = Intent(this, CreateCharacterActivity::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }
    
    private fun navigateToCharacterDetail(character: Character) {
        // TODO: Implement character detail view
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}