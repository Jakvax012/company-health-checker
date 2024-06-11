package com.example.company_health_checker.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.company_health_checker.data.CompanyProfile
import com.example.company_health_checker.data.TickerApiService
import com.example.company_health_checker.database.CompaniesRepository
import com.example.company_health_checker.database.FavoriteCompany
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(application: Application, private val repository: CompaniesRepository) : ViewModel() {
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

                insertCompanyIntoDatabase(companyProfile)
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

    fun toggleFavoriteCompany(company: CompanyProfile) {
        viewModelScope.launch {
            repository.getCompanyStream(company.symbol).firstOrNull()?.let { existingCompany ->
                if (existingCompany != null) {
                    repository.deleteCompany(existingCompany)
                } else {
                    repository.insertCompany(
                        FavoriteCompany(
                            ticker = company.symbol,
                            name = company.companyName ?: "N/A",
                            image = company.image
                        )
                    )
                }
            }
        }
    }

    fun isCompanyFavorite(ticker: String): Flow<Boolean> {
        return repository.getCompanyStream(ticker).map { it != null }
    }

    private fun insertCompanyIntoDatabase(companyProfile: CompanyProfile) {
        viewModelScope.launch {
            val favoriteCompany = FavoriteCompany(
                ticker = companyProfile.symbol,
                name = companyProfile.companyName ?: "",
                image = companyProfile.image
            )
            repository.insertCompany(favoriteCompany)
        }
    }
}