package com.romanvytv.inbrief.ui.feature.player

enum class PlaybackSpeed(val speed: Float) {
    SPEED_0_75(0.75f),
    SPEED_1_0(1.0f),
    SPEED_1_25(1.25f),
    SPEED_1_5(1.5f),
    SPEED_2_0(2.0f);

    fun next(): PlaybackSpeed {
        val index = PlaybackSpeed.entries.indexOf(this)
        return if (index < PlaybackSpeed.entries.toTypedArray().lastIndex)
            PlaybackSpeed.entries[index + 1]
        else
            SPEED_0_75
    }

    companion object {
        val DEFAULT = SPEED_1_0
    }
}