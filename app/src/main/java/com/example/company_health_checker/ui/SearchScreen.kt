package com.example.company_health_checker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.company_health_checker.R
import com.example.company_health_checker.Screen
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {
    val searchUiState by searchViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchTitle()
        Spacer(modifier = Modifier.weight(0.5f))
        SearchBar(
            isInputWrong = searchUiState.isInputWrong,
            userInput = searchViewModel.userInput,
            onUserInputChange = { searchViewModel.updateUserInput(it) },
            onKeyboardDone = {
                // funkcia checkUserInput bude vykonaná v rámci coroutine a po jej vykonaní sa skontroluje stav isInputWrong
                // bez tohto sa nestihla aktualizovať isInputWrong a tým pádom sa mohlo prejsť na druhú obrazovku aj bez toho, aby bol ticker správny
                coroutineScope.launch {
                    searchViewModel.checkUserInput()
                    if (!searchViewModel.uiState.value.isInputWrong) {
                        navController.navigate(Screen.CompanyScreen.name)
                    }
                }
            },
        )
        Button(
            onClick = { navController.navigate(Screen.LastViewedScreen.name) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("View Last Viewed Companies")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SearchBar(
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    isInputWrong: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
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
            value = userInput,
            onValueChange = onUserInputChange,
            label = {
                Text(
                    text = if (isInputWrong) {
                        stringResource(R.string.invalid_ticker)
                    } else {
                        stringResource(R.string.enter_ticker)
                    },
                    color = if (isInputWrong) Color.Red else Color.Unspecified
                )
            },
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isInputWrong) Color.Red else Color(0xFF7B27FF),
                unfocusedBorderColor = if (isInputWrong) Color.Red else Color(0xFFADADAD),
                unfocusedTextColor = Color(0xFFADADAD),
                unfocusedLabelColor = if (isInputWrong) Color.Red else Color(0xFFADADAD),
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        )
        Button(
            onClick = onKeyboardDone,
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
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(20.dp, 10.dp)
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
