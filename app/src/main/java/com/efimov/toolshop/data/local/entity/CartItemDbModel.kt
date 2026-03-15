package com.efimov.toolshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efimov.toolshop.domain.model.Product

@Entity(tableName = "cart_items")
data class CartItemDbModel(
    @PrimaryKey val productId: Int,
    val productJson: String,
    val product: Product,
    val quantity: Int,
    val startDate: String?,
    val endDate: String?
)
