package com.example.edunovel.presentation.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.usecase.character.*
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val createCharacterUseCase: CreateCharacterUseCase,
    private val getUserCharactersUseCase: GetUserCharactersUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase,
    private val userPreferences: com.example.edunovel.data.local.preferences.UserPreferences
) : ViewModel() {
    
    private val _characters = MutableLiveData<Resource<List<Character>>>()
    val characters: LiveData<Resource<List<Character>>> = _characters
    
    private val _createCharacterState = MutableLiveData<Resource<Long>>()
    val createCharacterState: LiveData<Resource<Long>> = _createCharacterState
    
    private val _updateCharacterState = MutableLiveData<Resource<Unit>>()
    val updateCharacterState: LiveData<Resource<Unit>> = _updateCharacterState
    
    private val _deleteCharacterState = MutableLiveData<Resource<Unit>>()
    val deleteCharacterState: LiveData<Resource<Unit>> = _deleteCharacterState
    
    private var currentUserId: Long = 0L
    
    init {
        viewModelScope.launch {
            userPreferences.userSession.collect { session ->
                currentUserId = session.userId
                if (session.isLoggedIn) {
                    loadCharacters()
                }
            }
        }
    }
    
    fun loadCharacters() {
        if (currentUserId == 0L) return
        getUserCharactersUseCase(currentUserId).onEach { result ->
            _characters.value = result
        }.launchIn(viewModelScope)
    }
    
    fun createCharacter(character: Character) {
        val newCharacter = character.copy(userId = currentUserId)
        createCharacterUseCase(newCharacter).onEach { result ->
            _createCharacterState.value = result
            if (result is Resource.Success) {
                loadCharacters()
            }
        }.launchIn(viewModelScope)
    }
    
    fun updateCharacter(character: Character) {
        updateCharacterUseCase(character).onEach { result ->
            _updateCharacterState.value = result
            if (result is Resource.Success) {
                loadCharacters()
            }
        }.launchIn(viewModelScope)
    }
    
    fun deleteCharacter(characterId: Long) {
        deleteCharacterUseCase(currentUserId, characterId).onEach { result ->
            _deleteCharacterState.value = result
            if (result is Resource.Success) {
                loadCharacters()
            }
        }.launchIn(viewModelScope)
    }
}