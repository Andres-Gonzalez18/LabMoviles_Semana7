package com.example.bookfinder.data.remote

import com.example.bookfinder.data.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 20
    ): BooksResponse
}