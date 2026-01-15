package com.example.juragandiskon.domain.model

// Interface Strategy: Kontrak untuk semua jenis diskon
interface DiscountStrategy {
    fun calculateFinalPrice(originalPrice: Double): Double
}

// Implementasi 1: Diskon Persen (Misal: 50% Off)
class PercentDiscount(private val percentage: Double) : DiscountStrategy {
    override fun calculateFinalPrice(originalPrice: Double): Double {
        return originalPrice - (originalPrice * (percentage / 100))
    }
}

// Implementasi 2: Buy N Get Free (Misal: Beli 2 Gratis 1)
// Harga per item jadi berubah rata-ratanya
class BuyNGetFreeDiscount(private val buyQty: Int, private val getQty: Int) : DiscountStrategy {
    override fun calculateFinalPrice(originalPrice: Double): Double {
        val totalQty = buyQty + getQty
        val totalPrice = originalPrice * buyQty // Bayar cuma sejumlah buyQty
        return totalPrice / totalQty // Harga rata-rata per item
    }
}

// Implementasi 3: Diskon Jebakan (Ada Maksimal Potongan)
class TrapDiscount(private val percentage: Double, private val maxCut: Double) : DiscountStrategy {
    override fun calculateFinalPrice(originalPrice: Double): Double {
        val rawCut = originalPrice * (percentage / 100)
        val realCut = if (rawCut > maxCut) maxCut else rawCut
        return originalPrice - realCut
    }
}