package com.example.juragandiskon.data.repository

import android.content.Context
import com.example.juragandiskon.domain.repository.GameRepository
// Import kelas DatabaseHelper SQLite buatanmu

class GameRepositoryImpl(private val context: Context) : GameRepository {
    
    // Anggaplah ini koneksi ke SQLite Helper
    // private val dbHelper = DatabaseHelper(context) 

    override fun saveWinningItem(itemName: String, price: Double) {
        // Logika insert ke SQLite ada di sini
        // dbHelper.insertItem(itemName, price)
    }

    override fun getAllInventory(): List<String> {
        // Logika select * from table ada di sini
        return listOf("Contoh Item 1", "Contoh Item 2") 
    }
}