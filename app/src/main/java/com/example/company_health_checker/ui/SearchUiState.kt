package com.example.company_health_checker.ui

import com.example.company_health_checker.data.CompanyProfile
import com.example.company_health_checker.data.CompanyRatios

data class SearchUiState(
    val isInputWrong: Boolean = false,
    val companyProfile: CompanyProfile? = null,
    val companyRatios: CompanyRatios? = null
)
