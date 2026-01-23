package com.example.edunovel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Long = 0,
    val name: String,
    val subject: String,
    val personality: String,
    val description: String = "",
    val imageUrl: String = "",
    val isDefault: Boolean = false,
    val userId: Long,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable