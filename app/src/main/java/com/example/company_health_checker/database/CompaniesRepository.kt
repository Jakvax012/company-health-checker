package com.example.company_health_checker.database

import kotlinx.coroutines.flow.Flow

interface CompaniesRepository {
    fun getAllCompaniesStream(): Flow<List<FavoriteCompany>>
    fun getCompanyStream(ticker: String): Flow<FavoriteCompany?>
    suspend fun insertCompany(favoriteCompany: FavoriteCompany)
    suspend fun deleteCompany(favoriteCompany: FavoriteCompany)
    suspend fun updateCompany(favoriteCompany: FavoriteCompany)
}