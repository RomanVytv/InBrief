package com.romanvytv.inbrief.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class MediaPlayerServiceConnection(val context: Context) : ServiceConnection,
    IMediaPlayerServiceConnection {

    override var controller: MediaPlayerController? = null

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> get() = _isConnected

    override val playbackPosition: StateFlow<Int>
        get() = controller?.playbackPosition ?: MutableStateFlow(0)

    override val audioCompleted: StateFlow<Boolean>
        get() = controller?.audioCompleted ?: MutableStateFlow(false)

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as? MediaPlayerService.MediaPlayerBinder
        controller = binder?.getController()
        _isConnected.value = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        controller = null
        _isConnected.value = false
    }

    override fun connect() {
        val intent = Intent(context, MediaPlayerService::class.java)
        context.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun disconnect() {
        context.unbindService(this)
    }

    override fun play() {
        controller?.play()
    }

    override fun pause() {
        controller?.pause()
    }

    override fun prepare(path: String) {
        controller?.prepare(path)
    }

    override fun setSpeed(speed: Float) {
        controller?.setSpeed(speed)
    }

    override fun seekTo(seconds: Int) {
        controller?.seekTo(seconds)
    }
}
