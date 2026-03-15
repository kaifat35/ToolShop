package com.efimov.toolshop.domain.model

import java.math.BigDecimal

data class Product(
    val id: Int,
    val ownerId: Int,
    val name: String,
    val description: String?,
    val pricePerDay: BigDecimal,
    val categoryId: Int,
    val quantity: Int,
    val image: List<String>
)
