package com.efimov.toolshop.domain.model

data class Tool(
    val id: Int,
    val nameTool: String,
    val priceTool: Int,
    val isInStock: Boolean
)
