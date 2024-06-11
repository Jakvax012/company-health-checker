package com.example.company_health_checker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.company_health_checker.database.AppDataContainer
import com.example.company_health_checker.ui.CompanyScreen
import com.example.company_health_checker.ui.SearchScreen
import com.example.company_health_checker.ui.SearchViewModel
import com.example.company_health_checker.ui.SearchViewModelFactory
import com.example.company_health_checker.ui.theme.Company_Health_CheckerTheme

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppDataContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppDataContainer(this)
        val searchViewModel: SearchViewModel by viewModels {
            SearchViewModelFactory(application, appContainer.companiesRepository)
        }
        setContent {
            Company_Health_CheckerTheme {
                MainScreen(searchViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(searchViewModel: SearchViewModel) {
    val navController = rememberNavController()
    AppNavigation(navController = navController, searchViewModel = searchViewModel)
}