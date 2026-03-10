package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.db.ToolDao
import com.efimov.toolshop.data.mapper.toDomain
import com.efimov.toolshop.domain.model.Tool
import com.efimov.toolshop.domain.repository.ToolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolRepositoryImpl @Inject constructor(
    private val dao: ToolDao
) : ToolRepository {

    override fun getAllTools(): Flow<List<Tool>> =
        dao.getAllTools().map { tools ->
            tools.map { it.toDomain() }
        }

    override suspend fun getToolById(id: Int): Tool? =
        dao.getToolById(id)?.toDomain()

    override suspend fun addToolCart(isInStock: Boolean, id: Int) {
        val tool = dao.getToolById(id)
        if (tool != null && tool.isInStock) {
            dao.addToolCart(
                isInStock = true,
                id = tool.id
            )
        } else {
            throw IllegalArgumentException("Товар с id $id недоступен")
        }
    }

    override suspend fun deleteToolCart(id: Int) {
        dao.getToolById(id)?.let {
            dao.deleteToolCart(it)
        }
    }

    override suspend fun buyTool(id: Int) {
        dao.buyTool(id)
    }
}