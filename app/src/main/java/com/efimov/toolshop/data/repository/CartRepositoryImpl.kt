package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.db.AppDao
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
        return dao.getAllItems().map { items ->
            items.toEntities()
        }
    }

    override suspend fun addItem(item: CartItem) {
        return dao.insert(item)
    }

    override suspend fun removeItem(item: CartItem) {
        return dao.delete(item)
    }

    override suspend fun updateItem(item: CartItem) {
        return dao.updateItem(item)
    }

    override suspend fun clearAll() {
       return dao.clearAll()
    }

    override fun getItemCount(): Flow<Int> {
        return dao.getCount()
    }
}