package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.db.AppDao
import com.efimov.toolshop.data.local.mapper.toEntity
import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl@Inject constructor(
    private val dao: AppDao
)  : CategoryRepository {
    override suspend fun getCategories(): Flow<Category> {
        return dao.getCategories().map {
            it.toEntity()
        }
    }
}