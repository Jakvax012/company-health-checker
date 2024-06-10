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

@Composable
fun TopInfo() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Image(
            painter = painterResource(id = R.drawable.microsoft_logo_icon_png_transparent_background), // Replace with your image resource
            contentDescription = "Company Logo",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Microsoft Corporation",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "MSFT",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "$425.52",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7B27FF)
            )
            Text(
                text = "4:00 PM 04/05/24",
                fontSize = 14.sp,
                color = Color.Gray
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

@Composable
fun CompanyData() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Dividend information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompanyInfoBox("Dividend yield", "0.71 %")
            CompanyInfoBox("Dividend rate", "$3.00")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompanyInfoBox("Div. increase", "10 %")
            CompanyInfoBox("FCF payout ratio", "34 %")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Financial information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompanyInfoBox("EPS", "$9.81")
            CompanyInfoBox("EPS increase YoY", "9.31 %")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompanyInfoBox("FCPS", "$7.91")
            CompanyInfoBox("FCPS increase YoY", "-10.22 %")
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

@Composable
fun CompanyScreen() {
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopInfo()
        Divider(color = Color(0xFFADADAD), thickness = 1.dp)
        CompanyData()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompanyScreen() {
    CompanyScreen()
}