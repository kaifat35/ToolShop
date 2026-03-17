package com.efimov.toolshop.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.efimov.toolshop.domain.model.PaymentMethod
import com.efimov.toolshop.domain.model.PaymentStatus
import com.efimov.toolshop.presentation.viewmodel.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    orderId: String?,
    navController: NavController,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(orderId) {
        if (orderId != null) viewModel.loadPayment(orderId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Оплата заказа") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.errorMessage != null -> Text(uiState.errorMessage ?: "Ошибка")
                    else -> {
                        when (uiState.payment?.method) {
                            PaymentMethod.SBP -> {
                                Text(
                                    "Оплата по СБП",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                val sbpLink = uiState.payment?.sbpQrCode
                                if (!sbpLink.isNullOrBlank()) {
                                    AsyncImage(
                                        model = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=$sbpLink",
                                        contentDescription = "QR-код для оплаты по СБП",
                                        modifier = Modifier.size(200.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = {
                                            context.startActivity(
                                                Intent(Intent.ACTION_VIEW, Uri.parse(sbpLink))
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Открыть оплату в банковском приложении")
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Или отсканируйте QR-код в приложении банка")
                            }

                            PaymentMethod.CARD -> {
                                Text(
                                    "Оплата картой (МИР)",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Вы будете перенаправлены на защищённую платёжную страницу.")

                                val paymentLink = uiState.payment?.confirmationUrl
                                Button(
                                    onClick = {
                                        if (!paymentLink.isNullOrBlank()) {
                                            context.startActivity(
                                                Intent(Intent.ACTION_VIEW, Uri.parse(paymentLink))
                                            )
                                        }
                                    },
                                    enabled = !paymentLink.isNullOrBlank(),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Оплатить картой МИР")
                                }
                            }

                            null -> Unit
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.checkPaymentStatus() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Проверить статус оплаты")
                        }

                        if (uiState.isSuccess) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Оплата прошла успешно!", color = Color.Green)
                            Button(onClick = { navController.navigate("orders") }) {
                                Text("Перейти к заказам")
                            }
                        }
                    }
                }
            }
        }
    }
}