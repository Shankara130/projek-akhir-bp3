package com.example.juragandiskon.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.juragandiskon.domain.model.DiscountStrategy
import com.example.juragandiskon.domain.usecase.ComparePriceUseCase

class GameViewModel : ViewModel() {

    // Dependency (Bisa pakai DI Framework kayak Hilt, atau manual di Factory)
    private val compareUseCase = ComparePriceUseCase()

    private val _gameResult = MutableLiveData<String>()
    val gameResult: LiveData<String> = _gameResult

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