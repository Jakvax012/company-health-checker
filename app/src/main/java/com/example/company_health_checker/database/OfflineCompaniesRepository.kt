package com.example.company_health_checker.database

import kotlinx.coroutines.flow.Flow

class OfflineCompaniesRepository(private val favoriteCompanyDao: FavoriteCompanyDao) : CompaniesRepository {
    override fun getAllCompaniesStream(): Flow<List<FavoriteCompany>> = favoriteCompanyDao.getAllCompanies()
    override fun getCompanyStream(ticker: String): Flow<FavoriteCompany?> = favoriteCompanyDao.getCompany(ticker)
    override suspend fun insertCompany(favoriteCompany: FavoriteCompany) = favoriteCompanyDao.insert(favoriteCompany)
    override suspend fun updateCompany(favoriteCompany: FavoriteCompany) = favoriteCompanyDao.update(favoriteCompany)
    override suspend fun deleteCompany(favoriteCompany: FavoriteCompany) = favoriteCompanyDao.delete(favoriteCompany)
}