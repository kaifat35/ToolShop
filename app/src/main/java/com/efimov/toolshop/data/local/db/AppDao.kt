package com.efimov.toolshop.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.efimov.toolshop.data.local.entity.CartItemDbModel
import com.efimov.toolshop.data.local.entity.CategoryDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM cart_items")
    fun getAllItems(): Flow<List<CartItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemDbModel)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCount(): Flow<Int>

    @Update
    suspend fun updateItem(item: CartItemDbModel)

    @Query("SELECT * FROM category")
    suspend fun getCategories(): List<CategoryDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(items: List<CategoryDbModel>)
}