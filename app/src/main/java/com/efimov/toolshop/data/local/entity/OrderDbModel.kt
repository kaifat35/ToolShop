package com.efimov.toolshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efimov.toolshop.domain.model.DeliveryMethod
import com.efimov.toolshop.domain.model.OrderItem
import com.efimov.toolshop.domain.model.OrderStatus
import java.math.BigDecimal
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(tableName = "order_db")
data class OrderDbModel @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey val id: Int,
    val createdAt: Instant,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val deliveryMethod: DeliveryMethod,
    val address: String?,
    val comment: String?,
    val items: List<OrderItem>
)

