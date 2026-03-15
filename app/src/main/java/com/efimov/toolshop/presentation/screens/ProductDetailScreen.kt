package com.efimov.toolshop.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.efimov.toolshop.presentation.viewmodel.ProductDetailViewModel
import com.google.accompanist.pager.HorizontalPager
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int?,
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(productId) {
        if (productId != null) viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Детали товара") }) }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize()) { CircularProgressIndicator(Modifier.align(Alignment.Center)) }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Карусель изображений
                if (uiState.product.image.isNotEmpty()) {
                    HorizontalPager(
                        count = uiState.product.image.size,
                        modifier = Modifier.height(200.dp)
                    ) { page ->
                        AsyncImage(
                            model = uiState.product.image[page],
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = uiState.product.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "${uiState.product.pricePerDay} ₽/сутки", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    DateRangePicker(
                        startDate = uiState.startDate,
                        endDate = uiState.endDate,
                        onStartDateChange = { viewModel.updateStartDate(it) },
                        onEndDateChange = { viewModel.updateEndDate(it) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Доступно: ${uiState.availableQuantity} шт.")

                    Button(
                        onClick = {
                            if (uiState.startDate != null && uiState.endDate != null) {
                                viewModel.addToCart()
                                Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        },
                        enabled = uiState.startDate != null && uiState.endDate != null && uiState.availableQuantity > 0,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Добавить в корзину")
                    }
                }
            }
        }
    }
}

@Composable
fun DateRangePicker(
    startDate: LocalDate?,
    endDate: LocalDate?,
    onStartDateChange: (LocalDate) -> Unit,
    onEndDateChange: (LocalDate) -> Unit,
) {
    val today = LocalDate.now()

    Row {
        OutlinedButton(onClick = { onStartDateChange(today) }) {
            Text(startDate?.toString() ?: "Начало")
        }
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedButton(onClick = { onEndDateChange((startDate ?: today).plusDays(1)) }) {
            Text(endDate?.toString() ?: "Окончание")
        }
    }

    if (startDate != null && endDate != null) {
        val days = ChronoUnit.DAYS.between(startDate, endDate).coerceAtLeast(1)
        Text("Срок аренды: $days дн.")
    }
}
