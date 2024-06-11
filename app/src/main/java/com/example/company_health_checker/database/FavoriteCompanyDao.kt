package com.example.company_health_checker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCompanyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(company: FavoriteCompany)

    @Update
    suspend fun update(company: FavoriteCompany)

    @Delete
    suspend fun delete(company: FavoriteCompany)

    @Query("SELECT * from favorite_companies WHERE ticker = :ticker")
    fun getCompany(ticker: String): Flow<FavoriteCompany>

    @Query("SELECT * from favorite_companies ORDER BY name ASC")
    fun getAllCompanies(): Flow<List<FavoriteCompany>>
}