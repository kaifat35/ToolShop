package com.efimov.toolshop.domain.model

data class AvailabilityPeriod(
    val productId: Int,
    val bookedQuantity: Int,
    val startDate: java.time.LocalDate?,
    val endDate: java.time.LocalDate?,
)
