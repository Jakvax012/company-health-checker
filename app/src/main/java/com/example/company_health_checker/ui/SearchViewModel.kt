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

    fun checkUserInput() {
        viewModelScope.launch {
            try {
                val response = tickerApiService.getCompanyProfile(userInput)
                println("Odpoveď zo servera pre ticker $userInput: $response")
                if (response.isNotEmpty()) {
                    println("Ticker $userInput je platný.")
                    val companyData = response.first()
                    println("Symbol: ${companyData.symbol}")
                    println("Price: ${companyData.price}")
                    println("Beta: ${companyData.beta}")
                    println("Currency: ${companyData.currency}")
                    println("DCF: ${companyData.dcf}")
                    println("Image: ${companyData.image}")
                    _uiState.update { currentState ->
                        currentState.copy(isInputWrong = false)
                    }
                    // Fetch ratios data
                    try {
                        val ratiosResponse = tickerApiService.getCompanyRatios(userInput)
                        if (ratiosResponse.isNotEmpty()) {
                            val ratiosData = ratiosResponse.first()
                            println("Dividend Yield Percentage TTM: ${ratiosData.dividendYielPercentageTTM}")
                            println("PE Ratio TTM: ${ratiosData.peRatioTTM}")
                            println("Payout Ratio TTM: ${ratiosData.payoutRatioTTM}")
                            println("Dividend Per Share TTM: ${ratiosData.dividendPerShareTTM}")
                            println("Return On Equity TTM: ${ratiosData.returnOnEquityTTM}")
                            println("Return On Capital Employed TTM: ${ratiosData.returnOnCapitalEmployedTTM}")
                            println("Gross Profit Margin TTM: ${ratiosData.grossProfitMarginTTM}")
                            println("Operating Profit Margin TTM: ${ratiosData.operatingProfitMarginTTM}")
                            println("Debt Equity Ratio TTM: ${ratiosData.debtEquityRatioTTM}")
                            println("Operating Cash Flow Per Share TTM: ${ratiosData.operatingCashFlowPerShareTTM}")
                            println("Free Cash Flow Per Share TTM: ${ratiosData.freeCashFlowPerShareTTM}")
                            println("Price To Operating Cash Flows Ratio TTM: ${ratiosData.priceToOperatingCashFlowsRatioTTM}")
                        } else {
                            println("Neboli nájdené žiadne údaje pre ticker $userInput z druhého endpointu.")
                        }
                    } catch (e: Exception) {
                        println("Chyba pri získavaní údajov z druhého endpointu: ${e.message}")
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
}