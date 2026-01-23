package com.example.edunovel.data.local.database.dao

import androidx.room.*
import com.example.edunovel.data.local.database.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity): Long
    
    @Update
    suspend fun updateCharacter(character: CharacterEntity)
    
    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)
    
    @Query("SELECT * FROM characters WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Long): CharacterEntity?
    
    @Query("SELECT * FROM characters WHERE userId = :userId OR isDefault = 1")
    fun getUserCharacters(userId: Long): Flow<List<CharacterEntity>>
    
    @Query("SELECT * FROM characters WHERE subject = :subject AND (userId = :userId OR isDefault = 1)")
    fun getCharactersBySubject(userId: Long, subject: String): Flow<List<CharacterEntity>>
    
    @Query("SELECT * FROM characters WHERE isDefault = 1")
    suspend fun getDefaultCharacters(): List<CharacterEntity>
    
    @Query("DELETE FROM characters WHERE userId = :userId AND id = :characterId")
    suspend fun deleteUserCharacter(userId: Long, characterId: Long)
}