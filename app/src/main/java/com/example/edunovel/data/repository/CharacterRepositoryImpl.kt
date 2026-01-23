package com.example.edunovel.data.repository

import com.example.edunovel.data.local.database.dao.CharacterDao
import com.example.edunovel.data.local.database.entity.CharacterEntity
import com.example.edunovel.domain.model.Character
import com.example.edunovel.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val characterDao: CharacterDao
) : CharacterRepository {
    
    override suspend fun insertCharacter(character: Character): Long {
        return characterDao.insertCharacter(character.toEntity())
    }
    
    override suspend fun updateCharacter(character: Character) {
        characterDao.updateCharacter(character.toEntity())
    }
    
    override suspend fun deleteCharacter(character: Character) {
        characterDao.deleteCharacter(character.toEntity())
    }
    
    override suspend fun getCharacterById(characterId: Long): Character? {
        return characterDao.getCharacterById(characterId)?.toDomain()
    }
    
    override fun getUserCharacters(userId: Long): Flow<List<Character>> {
        return characterDao.getUserCharacters(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun getCharactersBySubject(userId: Long, subject: String): Flow<List<Character>> {
        return characterDao.getCharactersBySubject(userId, subject).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getDefaultCharacters(): List<Character> {
        return characterDao.getDefaultCharacters().map { it.toDomain() }
    }
    
    override suspend fun deleteUserCharacter(userId: Long, characterId: Long) {
        characterDao.deleteUserCharacter(userId, characterId)
    }
    
    // Extension functions
    private fun Character.toEntity() = CharacterEntity(
        id = id,
        userId = userId,
        name = name,
        imageUrl = imageUrl,
        personality = personality,
        subject = subject,
        description = description,
        isDefault = isDefault,
        createdAt = createdAt
    )
    
    private fun CharacterEntity.toDomain() = Character(
        id = id,
        userId = userId,
        name = name,
        imageUrl = imageUrl,
        personality = personality,
        subject = subject,
        description = description,
        isDefault = isDefault,
        createdAt = createdAt
    )
}