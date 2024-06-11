package com.example.company_health_checker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_companies")
data class FavoriteCompany(
    @PrimaryKey
    val ticker: String,
    val name: String,
    val image: String
)

