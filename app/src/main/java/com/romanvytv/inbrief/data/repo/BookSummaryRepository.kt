package com.romanvytv.inbrief.data.repo

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.romanvytv.inbrief.METADATA_PATH
import com.romanvytv.inbrief.data.entity.BookSummary
import com.romanvytv.inbrief.data.entity.BookSummaryWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class BookSummaryRepository(private val context: Context) : IBookSummaryRepository {

    override suspend fun getBookSummary(contentPath: String): BookSummary? {
        return loadBookSummaryFromJson("$contentPath/$METADATA_PATH")
    }

    private suspend fun loadBookSummaryFromJson(path: String): BookSummary? =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Loading book summary from assets...")
            runCatching {
                context.assets.open(path).use { inputStream ->
                    InputStreamReader(inputStream).use { reader ->
                        Gson().fromJson(reader, BookSummaryWrapper::class.java).summary
                    }
                }
            }.onSuccess { summary ->
                Log.d(TAG, "Loaded book: ${summary.bookTitle}, chapters: ${summary.chapters.size}")
            }.onFailure { error ->
                Log.e(TAG, "Failed to load or parse JSON", error)
            }.getOrNull()
        }

    companion object {
        const val TAG = "BookSummaryRepository"
    }
}