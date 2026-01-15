package com.example.juragandiskon.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juragandiskon.domain.model.InventoryItem
import com.example.juragandiskon.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    // LiveData untuk menampung list barang dari database
    private val _inventoryList = MutableLiveData<List<InventoryItem>>()
    val inventoryList: LiveData<List<InventoryItem>> = _inventoryList

    // Fungsi untuk memuat data dari database (dijalankan di Background Thread)
    fun loadInventory() {
        viewModelScope.launch(Dispatchers.IO) {
            // Ambil data dari repository
            val items = repository.getAllInventory()
            
            // Posting nilai ke LiveData (agar UI di Main Thread bisa update)
            _inventoryList.postValue(items)
        }
    }

    fun checkAnswer(
        priceA: Double, promoA: DiscountStrategy,
        priceB: Double, promoB: DiscountStrategy
    ) {
        // Panggil UseCase (Bisnis Logic)
        val result = compareUseCase.execute(priceA, promoA, priceB, promoB)
        
        // Update UI
        _gameResult.value = result
    }
}