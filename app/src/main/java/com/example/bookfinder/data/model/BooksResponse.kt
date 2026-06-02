package com.example.bookfinder.data.model

data class BooksResponse(
    val totalItems: Int = 0,
    val items: List<BookItem>? = emptyList()
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val imageLinks: ImageLinks?,
    val previewLink: String?
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)