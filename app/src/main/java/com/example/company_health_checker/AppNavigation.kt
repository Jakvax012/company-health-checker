package com.example.company_health_checker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.company_health_checker.ui.SearchScreen
import com.example.company_health_checker.ui.SearchViewModel
import com.example.company_health_checker.Screen
import com.example.company_health_checker.ui.CompanyScreen

enum class Screen {
    SearchScreen,
    CompanyScreen,
}
@Composable
fun AppNavigation(navController: NavHostController, searchViewModel: SearchViewModel) {
    NavHost(navController = navController, startDestination = com.example.company_health_checker.Screen.SearchScreen.name) {
        composable(com.example.company_health_checker.Screen.SearchScreen.name) {
            SearchScreen(navController = navController, searchViewModel = searchViewModel)
        }
        composable(com.example.company_health_checker.Screen.CompanyScreen.name) {
            CompanyScreen(searchViewModel = searchViewModel)
        }
    }
}