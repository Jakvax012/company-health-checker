package com.example.company_health_checker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.company_health_checker.R

@Preview(showBackground = true)
@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        SearchTitle()
        SearchBar()
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.enter_ticker_description),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFADADAD),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 15.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text(stringResource(R.string.enter_ticker))},
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF7B27FF),
                unfocusedBorderColor = Color(0xFFADADAD),
                unfocusedTextColor = Color(0xFFADADAD),
                unfocusedLabelColor = Color(0xFFADADAD),
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        )
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B27FF)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.search)
            )
        }
    }
}

@Composable
fun SearchTitle(modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(20.dp,10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 42.sp,
            lineHeight = 42.sp,
            textAlign = TextAlign.Center,
            color = Color(4278661441),
            fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.subtitle_search),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(400),
            color = Color(4278661441),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}