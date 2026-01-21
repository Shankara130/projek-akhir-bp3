package com.example.edunovel.domain.model

data class StoryChapter(
    val id: Long = 0,
    val title: String,
    val subject: String,
    val content: String,
    val characterId: Long,
    val order: Int = 0,
    val dialogues: List<Dialogue> = emptyList()
)

data class Dialogue(
    val speaker: String,
    val text: String,
    val characterImageUrl: String = ""
)