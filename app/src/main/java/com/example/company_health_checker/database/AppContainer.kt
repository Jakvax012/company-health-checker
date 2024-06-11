package com.example.company_health_checker.database

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val companiesRepository: CompaniesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineCompaniesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [OfflineCompaniesRepository]
     */
    override val companiesRepository: CompaniesRepository by lazy {
        OfflineCompaniesRepository(CompanyDatabase.getDatabase(context).favoriteCompanyDao())
    }
}