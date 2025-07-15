package com.romanvytv.inbrief.data.repo

import com.romanvytv.inbrief.data.entity.BookSummary

interface IBookSummaryRepository {
    suspend fun getBookSummary(contentPath: String): BookSummary?
}