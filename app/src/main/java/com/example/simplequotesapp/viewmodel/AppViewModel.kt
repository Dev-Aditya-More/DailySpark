package com.example.simplequotesapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplequotesapp.data.QuoteDto
import com.example.simplequotesapp.data.RetrofitInstance
import com.example.simplequotesapp.data.local.AppDatabase
import com.example.simplequotesapp.data.model.Quote
import com.example.simplequotesapp.data.repository.QuoteRepository
import com.example.simplequotesapp.data.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchQuote()
    }

    private val dao = AppDatabase.getDatabase(application).quoteDao()
    private val repository = QuoteRepository(dao)

    val favorites: Flow<List<Quote>>
        get() = repository.allFavorites.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun fetchQuote() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomQuote()
                _quote.value = response.first().toEntity() // 🔁 Convert from DTO to Room entity
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error loading quote"
            }
        }
    }

    fun addToFavorites(quote: Quote) {
        viewModelScope.launch {
            repository.insert(quote)
        }
    }

    fun removeFromFavorites(quote: Quote) {
        viewModelScope.launch {
            repository.delete(quote)
        }
    }
}
