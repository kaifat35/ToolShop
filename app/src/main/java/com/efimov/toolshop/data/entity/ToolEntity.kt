package com.efimov.toolshop.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tools")
data class ToolEntity(
    @PrimaryKey val id: Int,
    val nameTool: String,
    val priceTool: Int,
    val isInStock: Boolean
)
