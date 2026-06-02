package com.example.bookfinder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookfinder.R
import com.example.bookfinder.data.model.BookItem

class BooksAdapter(
    private val onClick: (BookItem) -> Unit
) : ListAdapter<BookItem, BooksAdapter.BookViewHolder>(BookDiffCallback()) {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover: ImageView = itemView.findViewById(R.id.imgCover)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthors: TextView = itemView.findViewById(R.id.tvAuthors)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)

        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        val info = book.volumeInfo

        holder.tvTitle.text = info.title ?: "Sin título"
        holder.tvAuthors.text = info.authors?.joinToString(", ") ?: "Autor desconocido"
        holder.tvDate.text = info.publishedDate ?: "Fecha no disponible"

        val imageUrl = info.imageLinks?.thumbnail
            ?.replace("http://", "https://")

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.imgCover)

        holder.itemView.setOnClickListener {
            onClick(book)
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<BookItem>() {

    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
        return oldItem == newItem
    }
}