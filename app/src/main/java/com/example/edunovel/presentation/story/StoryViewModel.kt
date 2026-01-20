package com.example.edunovel.presentation.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.data.local.preferences.UserPreferences
import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.model.Material
import com.example.edunovel.domain.repository.CharacterRepository
import com.example.edunovel.domain.usecase.progress.SaveProgressUseCase
import com.example.edunovel.domain.usecase.story.GetStoryContentUseCase
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StoryViewModel(
    private val getStoryContentUseCase: GetStoryContentUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    private val characterRepository: CharacterRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    private val _storyContent = MutableLiveData<Resource<List<Material>>>()
    val storyContent: LiveData<Resource<List<Material>>> = _storyContent
    
    private val _currentSlide = MutableLiveData<Int>()
    val currentSlide: LiveData<Int> = _currentSlide
    
    private val _character = MutableLiveData<Character?>()
    val character: LiveData<Character?> = _character
    
    private var currentUserId: Int = 0
    private var materials: List<Material> = emptyList()
    
    init {
        viewModelScope.launch {
            userPreferences.userSession.collect { session ->
                currentUserId = session.userId
            }
        }
        _currentSlide.value = 0
    }
    
    fun loadStory(chapterId: Int) {
        getStoryContentUseCase(chapterId).onEach { result ->
            _storyContent.value = result
            if (result is Resource.Success) {
                materials = result.data ?: emptyList()
                if (materials.isNotEmpty()) {
                    loadCharacter(materials[0].characterId)
                }
            }
        }.launchIn(viewModelScope)
    }
    
    private fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _character.value = characterRepository.getCharacterById(characterId)
        }
    }
    
    fun nextSlide() {
        val current = _currentSlide.value ?: 0
        if (current < materials.size - 1) {
            val nextIndex = current + 1
            _currentSlide.value = nextIndex
            loadCharacter(materials[nextIndex].characterId)
        }
    }
    
    fun previousSlide() {
        val current = _currentSlide.value ?: 0
        if (current > 0) {
            val prevIndex = current - 1
            _currentSlide.value = prevIndex
            loadCharacter(materials[prevIndex].characterId)
        }
    }
    
    fun saveProgress(chapterId: Int, subject: String, position: Int) {
        viewModelScope.launch {
            val progress = com.yourname.edunovel.domain.model.Progress(
                userId = currentUserId,
                chapterId = chapterId,
                subject = subject,
                isCompleted = position >= materials.size - 1,
                lastPosition = position
            )
            saveProgressUseCase(progress).collect { /* Handle result if needed */ }
        }
    }
    
    fun hasNext(): Boolean {
        val current = _currentSlide.value ?: 0
        return current < materials.size - 1
    }
    
    fun hasPrevious(): Boolean {
        val current = _currentSlide.value ?: 0
        return current > 0
    }
    
    fun getCurrentMaterial(): Material? {
        val current = _currentSlide.value ?: 0
        return if (current < materials.size) materials[current] else null
    }
}