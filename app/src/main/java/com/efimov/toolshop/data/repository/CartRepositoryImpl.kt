package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.db.AppDao
import com.efimov.toolshop.data.local.mapper.toDbModel
import com.efimov.toolshop.data.local.mapper.toEntities
import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dao: AppDao
) : CartRepository {

    override fun getAllItems(): Flow<List<CartItem>> {
        return dao.getAllItems().map { it.toEntities() }
    }

    override suspend fun addItem(item: CartItem) {
        dao.insert(item.toDbModel())
    }

    override suspend fun removeItem(item: CartItem) {
        dao.deleteByProductId(item.productId)
    }

    override suspend fun updateItem(item: CartItem) {
        dao.updateItem(item.toDbModel())
    }

    override suspend fun clearAll() {
        dao.clearAll()
    }

    override fun getItemCount(): Flow<Int> = dao.getCount()
}
