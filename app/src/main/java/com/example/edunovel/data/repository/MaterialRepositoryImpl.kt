package com.example.edunovel.data.repository

import com.example.edunovel.data.local.database.dao.MaterialDao
import com.example.edunovel.data.local.database.entity.MaterialEntity
import com.example.edunovel.domain.model.Material
import com.example.edunovel.domain.repository.MaterialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MaterialRepositoryImpl(
    private val materialDao: MaterialDao
) : MaterialRepository {
    
    override suspend fun insertMaterial(material: Material): Long {
        return materialDao.insertMaterial(material.toEntity())
    }
    
    override suspend fun insertMaterials(materials: List<Material>) {
        materialDao.insertMaterials(materials.map { it.toEntity() })
    }
    
    override fun getMaterialsBySubject(subject: String): Flow<List<Material>> {
        return materialDao.getMaterialsBySubject(subject).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getMaterialsByChapter(chapterId: Int): List<Material> {
        return materialDao.getMaterialsByChapter(chapterId).map { it.toDomain() }
    }
    
    override suspend fun getMaterialById(materialId: Long): Material? {
        return materialDao.getMaterialById(materialId)?.toDomain()
    }
    
    override fun getAllMaterials(): Flow<List<Material>> {
        return materialDao.getAllMaterials().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    private fun Material.toEntity() = MaterialEntity(
        id = id,
        title = title,
        subject = subject,
        chapterId = chapterId,
        content = content,
        type = type,
        orderIndex = orderIndex,
        createdAt = createdAt
    )
    
    private fun MaterialEntity.toDomain() = Material(
        id = id,
        title = title,
        subject = subject,
        chapterId = chapterId,
        content = content,
        type = type,
        orderIndex = orderIndex,
        createdAt = createdAt
    )
}