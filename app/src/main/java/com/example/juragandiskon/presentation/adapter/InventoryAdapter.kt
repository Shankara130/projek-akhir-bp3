package com.example.juragandiskon.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.juragandiskon.R
import com.example.juragandiskon.domain.model.InventoryItem

class InventoryAdapter(private val inventoryList: ArrayList<InventoryItem>) :
    RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    // Fungsi untuk memperbarui data saat ada perubahan (misal setelah insert)
    fun updateData(newItems: List<InventoryItem>) {
        inventoryList.clear()
        inventoryList.addAll(newItems)
        notifyDataSetChanged() // Memberitahu RecyclerView untuk refresh tampilan
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        // Mengubah XML layout menjadi Objek View
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        // Mengambil data sesuai posisi urutan
        val item = inventoryList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return inventoryList.size
    }

    // Inner Class ViewHolder: Bertugas memegang komponen UI
    class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Inisialisasi komponen UI dari XML
        private val tvName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvItemPrice)

        fun bind(item: InventoryItem) {
            tvName.text = item.name
            tvPrice.text = item.displayPrice // Sudah diformat "Rp xxx" dari Repository
            
            // Logika tambahan: Jika harganya mahal (misal > 50rb), warnanya bisa dibedakan
            // if (item.price > 50000) tvPrice.setTextColor(Color.RED)
        }
    }
}