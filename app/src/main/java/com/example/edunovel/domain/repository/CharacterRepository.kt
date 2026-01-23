package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun insertCharacter(character: Character): Long
    suspend fun updateCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
    suspend fun getCharacterById(characterId: Long): Character?
    fun getUserCharacters(userId: Long): Flow<List<Character>>
    fun getCharactersBySubject(userId: Long, subject: String): Flow<List<Character>>
    suspend fun getDefaultCharacters(): List<Character>
    suspend fun deleteUserCharacter(userId: Long, characterId: Long)
}