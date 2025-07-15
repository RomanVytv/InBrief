package com.romanvytv.inbrief.ui.data.repo

import android.content.Context
import com.google.gson.Gson
import com.romanvytv.inbrief.ui.data.entity.BookSummary
import com.romanvytv.inbrief.ui.data.entity.BookSummaryWrapper
import java.io.InputStreamReader

interface IBookSummaryRepository {
    fun getBookSummary(): BookSummary?
    fun getChapterAudioFileName(chapterNumber: Int): String?
    fun getAudioFilePath(audioName: String): String
}

class BookSummaryRepository(private val context: Context) : IBookSummaryRepository {

    fun getBookSummary(): BookSummary? {
        return loadBookSummaryFromJson()
    }

    fun getChapterAudioFileName(chapterNumber: Int): String? {
        val summary = getBookSummary() ?: return null
        return summary.chapters.find { it.number == chapterNumber }?.audioName
    }

    private fun loadBookSummaryFromJson(): BookSummary? =
        runCatching {
            context.assets.open("content/inteligent_investor/metadata.json").use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val wrapper = Gson().fromJson(reader, BookSummaryWrapper::class.java)
                    wrapper.summary
                }
            }
        }.onFailure { it.printStackTrace() }
            .getOrNull()

    fun getAudioFilePath(audioName: String): String {
        // Returns the relative path to the audio file in assets
        return "content/inteligent_investor/$audioName"
    }
} 