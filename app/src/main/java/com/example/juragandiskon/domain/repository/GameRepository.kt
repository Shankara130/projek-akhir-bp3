package com.example.juragandiskon.domain.repository

import com.example.juragandiskon.domain.model.InventoryItem

interface GameRepository {
    fun saveWinningItem(itemName: String, price: Double)
    fun getAllInventory(): List<InventoryItem>
}