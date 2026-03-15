package com.efimov.toolshop.domain.model


import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit


//для корзины
data class CartItem(
    val product: Product,
    val productId: Int,
    val productJson: String,
    val quantity: Int = 1,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
    ) {
    val totalPrice: BigDecimal
        get() = if (startDate != null && endDate != null) {
            val days = ChronoUnit.DAYS.between(startDate, endDate).toInt()
            product.pricePerDay * days.toBigDecimal() * quantity.toBigDecimal()
        } else BigDecimal.ZERO
}