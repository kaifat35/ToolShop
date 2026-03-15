package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategories(): Flow<Category>
}