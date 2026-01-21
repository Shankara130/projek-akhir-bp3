package com.example.edunovel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int = 0,
    val userId: Int,
    val name: String,
    val imageUri: String?,
    val personality: String,
    val subject: String,
    val description: String,
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable