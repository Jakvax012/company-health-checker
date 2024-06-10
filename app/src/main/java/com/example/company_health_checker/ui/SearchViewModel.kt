package com.example.company_health_checker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.company_health_checker.data.TickerApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    var userInput by mutableStateOf("")
        private set

    private val tickerApiService = TickerApiService.create()

    fun updateUserInput(input: String) {
        userInput = input
        if (_uiState.value.isInputWrong) {
            _uiState.update { currentState ->
                currentState.copy(isInputWrong = false)
            }
        }
    }

    fun checkUserInput() {
        viewModelScope.launch {
            try {
                val response = tickerApiService.getCompanyProfile(userInput)
                println("Odpoveď zo servera pre ticker $userInput: $response")
                if (response.isNotEmpty()) {
                    println("Ticker $userInput je platný.")
                    _uiState.update { currentState ->
                        currentState.copy(isInputWrong = false)
                    }
                } else {
                    println("Ticker $userInput je neplatný.")
                    _uiState.update { currentState ->
                        currentState.copy(isInputWrong = true)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(isInputWrong = true)
                }
            }
        }
    }
}