package com.example.edunovel.data.local.database.dao

import androidx.room.*
import com.example.edunovel.data.local.database.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: MaterialEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterials(materials: List)
    
    @Query("SELECT * FROM materials WHERE subject = :subject ORDER BY orderIndex ASC")
    fun getMaterialsBySubject(subject: String): Flow<List>
    
    @Query("SELECT * FROM materials WHERE chapterId = :chapterId ORDER BY orderIndex ASC")
    suspend fun getMaterialsByChapter(chapterId: Int): List
    
    @Query("SELECT * FROM materials WHERE id = :materialId")
    suspend fun getMaterialById(materialId: Int): MaterialEntity?
}