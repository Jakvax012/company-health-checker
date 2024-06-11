package com.example.company_health_checker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCompany::class], version = 1, exportSchema = false)
abstract class CompanyDatabase : RoomDatabase() {
    abstract fun favoriteCompanyDao() : FavoriteCompanyDao

    companion object {
        @Volatile
        private var Instance: CompanyDatabase? = null

        fun getDatabase(context: Context): CompanyDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CompanyDatabase::class.java, "company_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}