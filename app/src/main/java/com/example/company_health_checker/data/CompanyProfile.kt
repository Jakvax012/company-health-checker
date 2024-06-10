package com.example.company_health_checker.data

data class CompanyProfile(
    val symbol: String,
    val price: Double,
    val beta: Double,
    val currency: String,
    val companyName: String,
    val dcf: Double,
    val image: String,
)
