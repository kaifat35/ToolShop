package com.efimov.toolshop.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.efimov.toolshop.data.entity.ToolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolDao {

    @Query("SELECT * FROM tools ORDER BY id")
    fun getAllTools(): Flow<List<ToolEntity>>

    @Query("SELECT * FROM tools WHERE id = :id")
    suspend fun getToolById(id: Int): ToolEntity?

    @Insert
    suspend fun addToolCart(isInStock: Boolean, id: Int)

    @Delete
    suspend fun deleteToolCart(tool: ToolEntity)

    @Query("SELECT * FROM tools WHERE id = :id")
    suspend fun buyTool(id: Int)

}