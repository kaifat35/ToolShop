package com.efimov.toolshop.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.efimov.toolshop.presentation.viewmodel.ProfileViewModel


@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
}