package com.example.company_health_checker.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class CompanyProfile(val symbol: String, val name: String)

interface TickerApiService {
    @GET("profile/{ticker}")
    suspend fun getCompanyProfile(
        @Path("ticker") ticker: String,
        @Query("apikey") apiKey: String = "PrV6W7tS0N7U60N1ipF46tH66Qccto6A"
    ): List<CompanyProfile>

    companion object {
        private const val BASE_URL = "https://financialmodelingprep.com/api/v3/"

        fun create(): TickerApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(TickerApiService::class.java)
        }
    }
}