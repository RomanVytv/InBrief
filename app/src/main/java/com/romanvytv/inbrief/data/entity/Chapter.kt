package com.romanvytv.inbrief.data.entity

import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("number") val number: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("duration") val duration: Int = 0,
    @SerializedName("text") val text: String = "",
    @SerializedName("key_takeaway") val keyTakeaway: String = "",
    @SerializedName("audio_name") val audioName: String = ""
)