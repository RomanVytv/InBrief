package com.romanvytv.inbrief.data.entity

import com.google.gson.annotations.SerializedName

data class BookSummaryWrapper(
    @SerializedName("summary")
    val summary: BookSummary
)