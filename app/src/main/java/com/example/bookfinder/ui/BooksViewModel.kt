package com.example.bookfinder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.data.repository.BooksRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BooksViewModel : ViewModel() {

    private val repository = BooksRepository()

    private val _state = MutableLiveData<UiState>(UiState.Idle)
    val state: LiveData<UiState> = _state

    fun search(query: String) {
        if (query.isBlank()) {
            _state.value = UiState.Error("Escribe un término de búsqueda")
            return
        }

        _state.value = UiState.Loading

        viewModelScope.launch {
            try {
                val books = repository.searchBooks(query.trim())

                _state.value = if (books.isEmpty()) {
                    UiState.Error("No se encontraron resultados")
                } else {
                    UiState.Success(books)
                }

            } catch (e: IOException) {
                _state.value = UiState.Error("Sin conexión a Internet")
            } catch (e: HttpException) {
                _state.value = UiState.Error("Error HTTP ${e.code()}")
            } catch (e: Exception) {
                _state.value = UiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}