package com.example.simplequotesapp.data

import com.example.simplequotesapp.data.model.Quote

data class QuoteDto(
        val q: String, // quote
        val a: String  // author
)

fun QuoteDto.toEntity(): Quote = Quote(q = this.q, a = this.a)
