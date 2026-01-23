package com.example.edunovel.domain.repository

import com.example.edunovel.domain.model.Material
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun insertMaterial(material: Material): Long
    suspend fun insertMaterials(materials: List<Material>)
    fun getMaterialsBySubject(subject: String): Flow<List<Material>>
    suspend fun getMaterialsByChapter(chapterId: Int): List<Material>
    suspend fun getMaterialById(materialId: Long): Material?
    fun getAllMaterials(): Flow<List<Material>>
}