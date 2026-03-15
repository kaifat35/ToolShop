package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllItems(): Flow<List<CartItem>>
    fun getItemCount(): Flow<Int>
    suspend fun addItem(item: CartItem)
    suspend fun removeItem(item: CartItem)
    suspend fun updateItem(item: CartItem)
    suspend fun clearAll()
}