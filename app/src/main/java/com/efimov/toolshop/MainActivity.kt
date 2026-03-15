package com.efimov.toolshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.efimov.toolshop.data.local.datastore.UserPreferences
import com.efimov.toolshop.presentation.navigation.AppNavHost
import com.efimov.toolshop.presentation.ui.theme.ToolShopTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToolShopTheme {
                val navController = rememberNavController()
                val startDestination by viewModel<MainViewModel>().startDestination.collectAsState()

                AppNavHost(navController = navController, startDestination = startDestination ?: "auth")
            }
        }
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    userPreferences: UserPreferences
) : ViewModel() {
    val startDestination = userPreferences.getUserId().map { userId ->
        if (userId != null) "catalog" else "auth"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
