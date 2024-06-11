package com.example.company_health_checker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.company_health_checker.Screen
import com.example.company_health_checker.database.FavoriteCompany
import kotlinx.coroutines.launch

@Composable
fun LastViewedScreen(navController: NavController, searchViewModel: SearchViewModel) {
    val companies by searchViewModel.getAllCompanies().collectAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(companies) { company ->
            CompanyItem(company) {
                coroutineScope.launch {
                    searchViewModel.fetchCompanyData(company.ticker, true)
                    navController.navigate(Screen.CompanyScreen.name)
                }
            }
        }
    }
}

@Composable
fun CompanyItem(company: FavoriteCompany, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = company.image),
            contentDescription = "Company Logo",
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = company.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = company.ticker,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}