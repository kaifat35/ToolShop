package com.efimov.toolshop.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.efimov.toolshop.data.local.entity.CartItemDbModel
import com.efimov.toolshop.data.local.entity.CategoryDbModel
import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.model.CreateOrderRequest
import com.efimov.toolshop.domain.model.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM cart_items")
    fun getAllItems(): Flow<List<CartItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCount(): Flow<Int>

    @Update
    suspend fun updateItem(item: CartItem)

    @Query("SELECT * FROM category")
    suspend fun getCategories(): Flow<CategoryDbModel>

    @Query("SELECT * FROM order_db")
    suspend fun getOrder(orderId: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrder(): CreateOrderRequest

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPayment(): Payment
}