package com.example.edunovel.presentation.character

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.edunovel.R
import com.example.edunovel.databinding.ActivityCreateCharacterBinding
import com.example.edunovel.domain.model.Character
import com.example.edunovel.util.Constants
import com.example.edunovel.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCharacterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCreateCharacterBinding
    private val viewModel: CharacterViewModel by viewModel()
    
    private var selectedImageUri: Uri? = null
    private var editingCharacter: Character? = null
    private var isEditMode = false
    
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            loadImage(it)
        }
    }
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openImagePicker()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        checkEditMode()
        setupToolbar()
        setupSpinners()
        setupListeners()
        setupObservers()
    }
    
    private fun checkEditMode() {
        editingCharacter = intent.getParcelableExtra("character")
        isEditMode = editingCharacter != null
        
        editingCharacter?.let { character ->
            binding.etCharacterName.setText(character.name)
            binding.etDescription.setText(character.description)
            
            // Set spinner selections
            val personalityPosition = Constants.PERSONALITIES.indexOf(character.personality)
            if (personalityPosition >= 0) {
                binding.spinnerPersonality.setSelection(personalityPosition)
            }
            
            val subjectPosition = Constants.SUBJECTS.indexOf(character.subject)
            if (subjectPosition >= 0) {
                binding.spinnerSubject.setSelection(subjectPosition)
            }
            
            // Load image if exists
            character.imageUri?.let { uriString ->
                selectedImageUri = Uri.parse(uriString)
                loadImage(selectedImageUri!!)
            }
        }
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = if (isEditMode) "Edit Character" else "Create Character"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupSpinners() {
        // Personality Spinner
        val personalityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Constants.PERSONALITIES
        )
        personalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPersonality.adapter = personalityAdapter
        
        // Subject Spinner
        val subjectAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Constants.SUBJECTS
        )
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSubject.adapter = subjectAdapter
    }
    
    private fun setupListeners() {
        binding.ivCharacterImage.setOnClickListener {
            checkPermissionAndPickImage()
        }
        
        binding.btnSelectImage.setOnClickListener {
            checkPermissionAndPickImage()
        }
        
        binding.btnSave.setOnClickListener {
            saveCharacter()
        }
    }
    
    private fun setupObservers() {
        viewModel.createCharacterState.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.btnSave.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.btnSave.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Character created successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    binding.btnSave.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        viewModel.updateCharacterState.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.btnSave.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.btnSave.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Character updated successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    binding.btnSave.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun checkPermissionAndPickImage() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openImagePicker()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
    
    private fun openImagePicker() {
        pickImageLauncher.launch("image/*")
    }
    
    private fun loadImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.ic_character_placeholder)
            .error(R.drawable.ic_character_placeholder)
            .centerCrop()
            .into(binding.ivCharacterImage)
    }
    
    private fun saveCharacter() {
        val name = binding.etCharacterName.text.toString().trim()
        val personality = binding.spinnerPersonality.selectedItem.toString()
        val subject = binding.spinnerSubject.selectedItem.toString()
        val description = binding.etDescription.text.toString().trim()
        
        if (name.isEmpty()) {
            binding.etCharacterName.error = "Name is required"
            return
        }
        
        if (description.isEmpty()) {
            binding.etDescription.error = "Description is required"
            return
        }
        
        val character = Character(
            id = editingCharacter?.id ?: 0,
            userId = 0, // Will be set in ViewModel
            name = name,
            imageUri = selectedImageUri?.toString(),
            personality = personality,
            subject = subject,
            description = description,
            isDefault = false
        )
        
        if (isEditMode) {
            viewModel.updateCharacter(character)
        } else {
            viewModel.createCharacter(character)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}