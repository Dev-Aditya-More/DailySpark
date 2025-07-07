package com.example.simplequotesapp.data

import retrofit2.http.GET

interface QuoteApi {
    @GET("api/random")
    suspend fun getRandomQuote(): List<QuoteDto>
}
