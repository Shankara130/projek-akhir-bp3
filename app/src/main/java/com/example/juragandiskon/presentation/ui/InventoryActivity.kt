package com.example.juragandiskon.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juragandiskon.R
import com.example.juragandiskon.data.repository.GameRepositoryImpl
import com.example.juragandiskon.presentation.adapter.InventoryAdapter
import com.example.juragandiskon.presentation.viewmodel.GameViewModel
import com.example.juragandiskon.presentation.viewmodel.GameViewModelFactory

class InventoryActivity : AppCompatActivity() {

    // Deklarasi Variabel UI
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyState: TextView // Teks "Kosong" jika belum ada barang
    
    // Deklarasi Adapter & ViewModel
    private lateinit var inventoryAdapter: InventoryAdapter
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        // 1. Inisialisasi View (Sesuai ID di XML)
        recyclerView = findViewById(R.id.rvInventory)
        tvEmptyState = findViewById(R.id.tvEmptyState) // Pastikan ID ini ada di XML Anda

        // 2. Setup RecyclerView & Adapter
        setupRecyclerView()

        // 3. Setup ViewModel (Menggunakan Factory untuk Inject Repository)
        setupViewModel()

        // 4. Observasi Data (Menunggu data dari Database)
        observeData()

        // 5. Trigger Load Data Pertama Kali
        viewModel.loadInventory()
    }

    private fun setupRecyclerView() {
        // Inisialisasi adapter dengan list kosong dulu
        inventoryAdapter = InventoryAdapter(arrayListOf())
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@InventoryActivity)
            adapter = inventoryAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel() {
        // Kita butuh Repository untuk dikirim ke ViewModel
        val repository = GameRepositoryImpl(this) // 'this' adalah Context
        
        // Kita butuh Factory untuk membungkus Repository
        val factory = GameViewModelFactory(repository)

        // Inisialisasi ViewModel menggunakan Factory
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]
    }

    private fun observeData() {
        // Saat 'inventoryList' di ViewModel berubah (selesai ambil data DB), kode ini jalan
        viewModel.inventoryList.observe(this) { items ->
            
            // Masukkan data ke Adapter
            inventoryAdapter.updateData(items)

            // Logika Tampilan Kosong vs Ada Isi
            if (items.isEmpty()) {
                recyclerView.visibility = View.GONE
                tvEmptyState.visibility = View.VISIBLE
                tvEmptyState.text = "Gudang kosong melompong!\nAyo belanja dulu."
            } else {
                recyclerView.visibility = View.VISIBLE
                tvEmptyState.visibility = View.GONE
            }
        }
    }

    // Opsional: Refresh data setiap kali user kembali ke layar ini (Resume)
    override fun onResume() {
        super.onResume()
        viewModel.loadInventory()
    }
}