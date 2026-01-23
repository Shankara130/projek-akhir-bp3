package com.example.edunovel.domain.usecase.character

import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.repository.CharacterRepository
import com.example.edunovel.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetCharactersBySubjectUseCase(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(userId: Long, subject: String): Flow<Resource<List<Character>>> {
        return characterRepository.getCharactersBySubject(userId, subject)
            .map<List<Character>, Resource<List<Character>>> { 
                Resource.Success(it) 
            }
            .catch { emit(Resource.Error(it.localizedMessage ?: "Failed to load characters")) }
    }
}