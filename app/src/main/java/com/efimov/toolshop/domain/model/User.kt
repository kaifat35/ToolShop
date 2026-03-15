package com.efimov.toolshop.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val avatarUrl: String? = null
)
