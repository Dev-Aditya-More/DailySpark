package com.example.simplequotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.simplequotesapp.screens.QuoteScreen
import com.example.simplequotesapp.ui.theme.SimpleQuotesAppTheme
import com.example.simplequotesapp.viewmodel.QuoteViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<QuoteViewModel>() // the instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SimpleQuotesAppTheme {

                val quote by viewModel.quote.collectAsState()
                val favorites by viewModel.favorites.collectAsState(null)

                quote?.let { quo ->
                    val isFavorited = favorites?.any { fav -> fav.q == quo.q && fav.a == quo.a }

                    QuoteScreen(
                        quote = quo,
                        isFavorite = isFavorited == true,
                        onNewQuoteClick = { viewModel.fetchQuote() },
                        onAddToFavorites = { viewModel.addToFavorites(it) },
                        onRemoveFromFavorites = { viewModel.removeFromFavorites(it) }
                    )
                }
            }
        }
    }
}
