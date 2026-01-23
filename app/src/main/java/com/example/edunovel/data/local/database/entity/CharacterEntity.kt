package com.example.edunovel.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.edunovel.domain.model.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subject: String,
    val personality: String,
    val description: String,
    val imageUrl: String,
    val userId: Long,
    val createdAt: Long,
    val isDefault: Boolean = false
)

// Mapper functions
fun CharacterEntity.toDomainModel(): Character {
    return Character(
        id = id,
        name = name,
        subject = subject,
        personality = personality,
        description = description,
        imageUrl = imageUrl,
        userId = userId,
        createdAt = createdAt,
        isDefault = isDefault
    )
}

fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        subject = subject,
        personality = personality,
        description = description,
        imageUrl = imageUrl,
        userId = userId,
        createdAt = createdAt,
        isDefault = isDefault
    )
}