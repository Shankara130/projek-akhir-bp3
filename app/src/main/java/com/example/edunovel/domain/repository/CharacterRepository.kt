package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun insertCharacter(character: Character): Long
    suspend fun updateCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
    suspend fun getCharacterById(characterId: Int): Character?
    fun getUserCharacters(userId: Int): Flow<List>
    fun getCharactersBySubject(userId: Int, subject: String): Flow<List>
    suspend fun getDefaultCharacters(): List
    suspend fun deleteUserCharacter(userId: Int, characterId: Int)
}