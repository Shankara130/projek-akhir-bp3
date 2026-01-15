package com.example.juragandiskon.data.model

// Ini mencerminkan kolom di tabel SQLite
data class InventoryEntity(
    val id: Int = 0,
    val itemName: String,
    val price: Double,
    val dateAdded: String
)