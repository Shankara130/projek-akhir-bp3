package com.example.edunovel.domain.usecase.character

import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.repository.CharacterRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateCharacterUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(character: Character): Flow<Resource> = flow {
        try {
            emit(Resource.Loading())
            
            if (character.name.isBlank()) {
                emit(Resource.Error("Character name cannot be empty"))
                return@flow
            }
            
            if (character.subject.isBlank()) {
                emit(Resource.Error("Please select a subject"))
                return@flow
            }
            
            val id = repository.insertCharacter(character)
            emit(Resource.Success(id))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to create character"))
        }
    }
}