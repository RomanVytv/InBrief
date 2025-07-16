package com.romanvytv.inbrief.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MediaPlayerServiceConnection(val context: Context) : ServiceConnection {

    private var controller: MediaPlayerController? = null

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    val playbackPosition: StateFlow<Int>
        get() = controller?.playbackPosition ?: MutableStateFlow(0)

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as? MediaPlayerService.MediaPlayerBinder
        controller = binder?.getController()
        _isConnected.value = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        controller = null
        _isConnected.value = false
    }

    fun connect() {
        val intent = Intent(context, MediaPlayerService::class.java)
        context.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    fun disconnect() {
        context.unbindService(this)
    }

    fun play() = controller?.play()
    fun pause() = controller?.pause()
    fun stop() = controller?.stop()
    fun prepare(path: String) = controller?.prepare(path)
    fun setSpeed(speed: Float) = controller?.setSpeed(speed)
    fun seekTo(seconds: Int) = controller?.seekTo(seconds)
}
