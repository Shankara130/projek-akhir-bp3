package com.example.juragandiskon.domain.repository

interface GameRepository {
    fun saveWinningItem(itemName: String, price: Double)
    fun getAllInventory(): List<String>
}