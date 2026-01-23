package com.example.edunovel.domain.usecase.character

import com.example.edunovel.domain.repository.CharacterRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteCharacterUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(userId: Long, characterId: Long): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            repository.deleteUserCharacter(userId, characterId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to delete character"))
        }
    }
}