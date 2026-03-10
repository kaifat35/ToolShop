package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.Tool
import kotlinx.coroutines.flow.Flow

interface ToolRepository {

    fun getAllTools(): Flow<List<Tool>>

    suspend fun getToolById(id: Int): Tool?

    suspend fun addToolCart(isInStock: Boolean, id: Int)

    suspend fun deleteToolCart(id: Int)

    suspend fun buyTool(id: Int)

}