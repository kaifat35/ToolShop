package com.efimov.toolshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemDbModel(
    @PrimaryKey val productId: Int,
    val productJson: String,
    val quantity: Int,
    val startDate: String?,
    val endDate: String?
)
