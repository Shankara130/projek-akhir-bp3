package com.example.edunovel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryChapter(
    val id: Int,
    val title: String,
    val subject: String,
    val description: String,
    val isCompleted: Boolean = false,
    val progress: Int = 0 // 0-100%
) : Parcelable