package com.example.simplequotesapp.data.repository

import com.example.simplequotesapp.data.local.QuoteDao
import com.example.simplequotesapp.data.model.Quote
import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val dao: QuoteDao) {
    val allFavorites: Flow<List<Quote>> = dao.getAllQuotes()

    suspend fun insert(quote: Quote) = dao.insert(quote)
    suspend fun delete(quote: Quote) = dao.delete(quote)
}
