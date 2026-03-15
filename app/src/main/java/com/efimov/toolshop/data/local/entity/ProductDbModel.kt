package com.efimov.toolshop.data.local.entity

import java.math.BigDecimal
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDbModel(
    @PrimaryKey val id: Int,
    val ownerId: Int,
    val name: String,
    val description: String?,
    val pricePerDay: BigDecimal,
    val categoryId: Int,
    val quantity: Int,
    val image: List<String>
)
