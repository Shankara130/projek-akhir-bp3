package com.example.juragandiskon.data.repository

import android.content.Context
import com.example.juragandiskon.data.model.InventoryEntity
import com.example.juragandiskon.data.source.DatabaseHelper
import com.example.juragandiskon.domain.model.InventoryItem
import com.example.juragandiskon.domain.repository.GameRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameRepositoryImpl(context: Context) : GameRepository {

    // Inisialisasi DatabaseHelper
    private val dbHelper = DatabaseHelper(context)

    override fun saveWinningItem(itemName: String, price: Double) {
        // 1. Siapkan data tanggal hari ini
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // 2. Bungkus ke Entity Database
        val newItem = InventoryEntity(
            itemName = itemName,
            price = price,
            dateAdded = currentDate
        )

        // 3. Simpan ke SQLite via Helper
        dbHelper.insertItem(newItem)
    }

    override fun getAllInventory(): List<InventoryItem> {
        // 1. Ambil data mentah dari SQLite
        val entities = dbHelper.getAllItems()

        // 2. MAPPING: Ubah Entity Database -> Domain Model
        // Ini agar UI terima data yang sudah rapi (Formatting uang dilakukan di sini atau ViewModel)
        return entities.map { entity ->
            InventoryItem(
                name = entity.itemName,
                displayPrice = "Rp ${entity.price.toInt()}" // Format sederhana
            )
        }
    }
}