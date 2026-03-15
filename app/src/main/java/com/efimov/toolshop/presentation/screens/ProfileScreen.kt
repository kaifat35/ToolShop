package com.efimov.toolshop.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.efimov.toolshop.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Профиль") }) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Имя: ${uiState.name}")
                    Text("Email: ${uiState.email}")
                    Text("Телефон: ${uiState.phone}")
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("orders") }, modifier = Modifier.fillMaxWidth()) {
                Text("Мои заказы")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { navController.navigate("cart") }, modifier = Modifier.fillMaxWidth()) {
                Text("Корзина")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                viewModel.logout()
                navController.navigate("auth") {
                    popUpTo("catalog") { inclusive = true }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Выйти")
            }
        }
    }
}