package com.example.edunovel.domain.usecase.character

import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersBySubjectUseCase(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(subject: String): Flow<List<Character>> {
        return characterRepository.getCharactersBySubject(subject)
    }
}