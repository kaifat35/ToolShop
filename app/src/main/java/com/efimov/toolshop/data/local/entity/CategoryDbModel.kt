package com.efimov.toolshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val parentId: Int?
)
