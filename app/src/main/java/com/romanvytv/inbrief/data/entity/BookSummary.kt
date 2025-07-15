package com.romanvytv.inbrief.data.entity

import com.google.gson.annotations.SerializedName

data class BookSummary(
    @SerializedName("book_title") val bookTitle: String,
    @SerializedName("book_author") val bookAuthor: String,
    @SerializedName("published") val published: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("cover_image") val cover: String,
    val chapters: List<Chapter>
)

