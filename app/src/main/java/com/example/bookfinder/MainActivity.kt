package com.example.bookfinder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookfinder.ui.BooksAdapter
import com.example.bookfinder.ui.BooksViewModel
import com.example.bookfinder.ui.UiState

class MainActivity : AppCompatActivity() {

    private val viewModel: BooksViewModel by viewModels()
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val etQuery = findViewById<EditText>(R.id.etQuery)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val rvBooks = findViewById<RecyclerView>(R.id.rvBooks)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        adapter = BooksAdapter { book ->
            Toast.makeText(
                this,
                book.volumeInfo.title ?: "Libro seleccionado",
                Toast.LENGTH_SHORT
            ).show()
        }

        rvBooks.layoutManager = LinearLayoutManager(this)
        rvBooks.adapter = adapter

        btnSearch.setOnClickListener {
            viewModel.search(etQuery.text.toString())
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is UiState.Idle -> {
                    progressBar.visibility = View.GONE
                }

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    adapter.submitList(state.books)
                }

                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}