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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.company_health_checker.R
import coil.compose.rememberImagePainter
import com.example.company_health_checker.data.CompanyProfile

@Composable
fun CompanyScreen(searchViewModel: SearchViewModel) {
    val searchUiState by searchViewModel.uiState.collectAsState()
    val isFavorite = searchViewModel.isCompanyFavorite(searchUiState.companyProfile?.symbol ?: "").collectAsState(initial = false).value

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopInfo(searchUiState, isFavorite) { profile ->
            searchViewModel.toggleFavoriteCompany(profile)
        }
        Divider(color = Color(0xFFADADAD), thickness = 1.dp)
        CompanyData(searchUiState)
    }
}

@Composable
fun TopInfo(searchUiState: SearchUiState, isFavorite: Boolean, onFavoriteClick: (CompanyProfile) -> Unit) {
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
            Box(
                modifier = Modifier.width(50.dp), // Set a fixed width for the icon button
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { onFavoriteClick(profile) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun CompanyData(searchUiState: SearchUiState) {
    searchUiState.companyRatios?.let { ratios ->
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.dividend_information), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox(stringResource(id = R.string.dividend_yield), "${"%.2f".format(ratios.dividendYielPercentageTTM)} %" ?: "N/A")
                CompanyInfoBox(stringResource(id = R.string.dividend_rate), "${"%.2f".format(ratios.dividendPerShareTTM)} $" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox(stringResource(id = R.string.payout_ratio), "${"%.2f".format(ratios.payoutRatioTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.free_cash_flow), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox(stringResource(id = R.string.cash_flow_share), "${"%.2f".format(ratios.freeCashFlowPerShareTTM)}" ?: "N/A")
                CompanyInfoBox(stringResource(id = R.string.operating_cfs), "${"%.2f".format(ratios.operatingCashFlowPerShareTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox(stringResource(id = R.string.price_OCF), "${"%.2f".format(ratios.priceToOperatingCashFlowsRatioTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.return_on), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox( stringResource(id = R.string.roe), "${"%.2f".format(ratios.returnOnEquityTTM)}" ?: "N/A")
                CompanyInfoBox(stringResource(id = R.string.roce), "${"%.2f".format(ratios.returnOnCapitalEmployedTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox(stringResource(id = R.string.roa), "${"%.2f".format(ratios.returnOnAssetsTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Margins", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("Gross margin", "${"%.2f".format(ratios.grossProfitMarginTTM)}" ?: "N/A")
                CompanyInfoBox("Operating margin", "${"%.2f".format(ratios.operatingProfitMarginTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("Net margin", "${"%.2f".format(ratios.netProfitMarginTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Debt ratios", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("CF/debt ratio", "${"%.2f".format(ratios.cashFlowToDebtRatioTTM)}" ?: "N/A")
                CompanyInfoBox("Debt equity ratio", "${"%.2f".format(ratios.debtEquityRatioTTM)}" ?: "N/A")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Other ratios", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompanyInfoBox("P/E ratio", "${"%.2f".format(ratios.peRatioTTM)}" ?: "N/A")
                CompanyInfoBox("P/B ratio", "${"%.2f".format(ratios.priceToBookRatioTTM)}" ?: "N/A")
            }
            searchUiState.companyProfile?.let { profile ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Other information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CompanyInfoBox("Beta", "${"%.2f".format(profile.beta)}" ?: "N/A")
                    CompanyInfoBox("DCF", "${"%.2f".format(profile.dcf)}" ?: "N/A"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
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