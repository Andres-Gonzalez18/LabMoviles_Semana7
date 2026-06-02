package com.example.bookfinder.data.repository

import com.example.bookfinder.BuildConfig
import com.example.bookfinder.data.model.BookItem
import com.example.bookfinder.data.remote.RetrofitClient

class BooksRepository {

    suspend fun searchBooks(query: String): List<BookItem> {
        val response = RetrofitClient.api.searchBooks(
            query = query,
            apiKey = BuildConfig.GOOGLE_BOOKS_API_KEY
        )

        return response.items ?: emptyList()
    }
}