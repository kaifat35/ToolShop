package com.efimov.toolshop.data.mapper

import com.efimov.toolshop.data.entity.ToolEntity
import com.efimov.toolshop.domain.model.Tool

fun ToolEntity.toDomain(): Tool = Tool(
    id = id,
    nameTool = nameTool,
    priceTool = priceTool,
    isInStock = isInStock
)

fun Tool.toEntity(): ToolEntity = ToolEntity(
    id = id,
    nameTool = nameTool,
    priceTool = priceTool,
    isInStock = isInStock
)