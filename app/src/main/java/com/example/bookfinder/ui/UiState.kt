package com.example.bookfinder.ui

import com.example.bookfinder.data.model.BookItem

sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class Success(val books: List<BookItem>) : UiState()
    data class Error(val message: String) : UiState()
}