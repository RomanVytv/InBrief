package com.romanvytv.inbrief.fakes

import com.romanvytv.inbrief.data.entity.BookSummary
import com.romanvytv.inbrief.data.entity.Chapter
import com.romanvytv.inbrief.data.repo.IBookSummaryRepository

class FakeRepository : IBookSummaryRepository {
    override suspend fun getBookSummary(contentPath: String): BookSummary? {
        val chapters = listOf(
            Chapter(1, "Chapter 1", 120, "chapter_1", "chapter_1_key", "chapter_1.mp3")
        )
        return BookSummary(
            bookTitle = "The Intelligent Investor",
            cover = "cover.png",
            chapters = chapters,
            published = "2000",
            duration = 807,
            bookAuthor = "Benjamin Graham"
        )
    }
}