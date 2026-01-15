package com.example.juragandiskon.domain.usecase

import com.example.juragandiskon.domain.model.DiscountStrategy

class ComparePriceUseCase {

    // Fungsi murni untuk logika game
    fun execute(
        priceA: Double, strategyA: DiscountStrategy,
        priceB: Double, strategyB: DiscountStrategy
    ): String {
        val finalPriceA = strategyA.calculateFinalPrice(priceA)
        val finalPriceB = strategyB.calculateFinalPrice(priceB)

        return when {
            finalPriceA < finalPriceB -> "Opsi A Lebih Murah! (Hemat ${finalPriceB - finalPriceA})"
            finalPriceB < finalPriceA -> "Opsi B Lebih Murah! (Hemat ${finalPriceA - finalPriceB})"
            else -> "Harganya Sama Persis!"
        }
    }
}