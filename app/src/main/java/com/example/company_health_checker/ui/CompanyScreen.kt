package com.example.company_health_checker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.company_health_checker.R
import coil.compose.rememberImagePainter

@Composable
fun CompanyScreen(searchViewModel: SearchViewModel) {
    val searchUiState by searchViewModel.uiState.collectAsState()

    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopInfo(searchUiState)
        Divider(color = Color(0xFFADADAD), thickness = 1.dp)
        CompanyData(searchUiState)
    }
}

@Composable
fun TopInfo(searchUiState: SearchUiState) {
    searchUiState.companyProfile?.let { profile ->
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFE6E6E6), shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = rememberImagePainter(data = profile.image),
                    contentDescription = "Company Logo",
                    modifier = Modifier.size(75.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = profile.companyName ?: "N/A",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = profile.symbol,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${profile.price} $",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7B27FF)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CompanyData(searchUiState: SearchUiState) {
    searchUiState.companyRatios?.let { ratios ->
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Dividend information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("Dividend yield", "${"%.2f".format(ratios.dividendYielPercentageTTM)} %")
                CompanyInfoBox("Dividend rate", "${"%.2f".format(ratios.dividendPerShareTTM)} $")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("Payout ratio", "${"%.2f".format(ratios.payoutRatioTTM)}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Financial information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("EPS", "N/A")
                CompanyInfoBox("EPS increase YoY", "N/A")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("FCPS", "N/A")
                CompanyInfoBox("FCPS increase YoY", "N/A")
            }
        }
    }
}

@Composable
fun CompanyInfoBox(label: String, value: String) {
    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFADADAD), shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
            .width(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7B27FF))
    }
}