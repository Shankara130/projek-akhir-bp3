package com.example.juragandiskon.data.source

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.juragandiskon.data.model.InventoryEntity

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "juragan_diskon.db"
        private const val DATABASE_VERSION = 1

        // Nama Tabel & Kolom
        const val TABLE_INVENTORY = "tb_inventory"
        const val COL_ID = "id"
        const val COL_NAME = "item_name"
        const val COL_PRICE = "price"
        const val COL_DATE = "date_added"
    }

    // Materi PDF: Membuat Tabel saat aplikasi pertama dijalankan
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_INVENTORY (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_PRICE REAL,
                $COL_DATE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    // Materi PDF: Handle perubahan versi database
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_INVENTORY")
        onCreate(db)
    }

    // --- CRUD Operation (Create) ---
    fun insertItem(item: InventoryEntity): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, item.itemName)
            put(COL_PRICE, item.price)
            put(COL_DATE, item.dateAdded)
        }
        // Insert return ID baris baru, atau -1 jika gagal
        val result = db.insert(TABLE_INVENTORY, null, values)
        db.close()
        return result
    }

    // --- CRUD Operation (Read) ---
    fun getAllItems(): List<InventoryEntity> {
        val itemList = ArrayList<InventoryEntity>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_INVENTORY"
        
        val cursor = db.rawQuery(selectQuery, null)

        // Looping cursor untuk mengambil data baris per baris
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))

                itemList.add(InventoryEntity(id, name, price, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return itemList
    }
}