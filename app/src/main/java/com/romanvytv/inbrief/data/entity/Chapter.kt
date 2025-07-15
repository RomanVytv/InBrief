package com.romanvytv.inbrief.data.entity

import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("number") val number: Int,
    @SerializedName("name")  val name: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("text") val text: String,
    @SerializedName("key_takeaway")  val keyTakeaway: String,
    @SerializedName("audio_name") val audioName: String
)