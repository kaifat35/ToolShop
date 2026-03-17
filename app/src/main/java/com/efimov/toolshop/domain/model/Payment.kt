package com.efimov.toolshop.domain.model

import java.math.BigDecimal

data class Payment(
    val id: String,
    val orderId: String,
    val amount: BigDecimal,
    val method: PaymentMethod,
    val status: PaymentStatus,
    val sbpQrCode: String?,
    val confirmationUrl: String? = null
)

enum class PaymentMethod { CARD, SBP }
enum class PaymentStatus { PENDING, SUCCESS, FAILED }
