package com.example.company_health_checker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.company_health_checker.Screen
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

    //private val tickerApiService = TickerApiService.create()
    private val tickerApiService: TickerApiService = TickerApiService.create()

    fun updateUserInput(input: String) {
        userInput = input
        if (_uiState.value.isInputWrong) {
            _uiState.update { currentState ->
                currentState.copy(isInputWrong = false)
            }
        }
    }

    suspend fun checkUserInput() {
        // Company data - first endpoint
        try {
            val profileResponse = tickerApiService.getCompanyProfile(userInput)
            if (profileResponse.isNotEmpty()) {
                val companyProfile = profileResponse.first()
                _uiState.update { currentState ->
                    currentState.copy(
                        isInputWrong = false,
                        companyProfile = companyProfile,
                    )
                }

                // Ratios data - second endpoint, start only if Ticker was correct
                // and response from first endpoint was not empty
                try {
                    val ratiosResponse = tickerApiService.getCompanyRatios(userInput)
                    if (ratiosResponse.isNotEmpty()) {
                        val companyRatios = ratiosResponse.first()
                        _uiState.update { currentState ->
                            currentState.copy(
                                companyRatios = companyRatios
                            )
                        }
                    }
                } catch (e: Exception) {
                    println("Error retrieving data from second endpoint: ${e.message}")
                }
            } else {
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