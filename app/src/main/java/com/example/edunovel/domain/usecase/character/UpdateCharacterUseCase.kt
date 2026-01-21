package com.example.edunovel.domain.usecase.character

import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.repository.CharacterRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateCharacterUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(character: Character): Flow<Resource> = flow {
        try {
            emit(Resource.Loading())
            repository.updateCharacter(character)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to update character"))
        }
    }
}