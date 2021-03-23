package com.example.shoppingapplication.util

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class MediaService :Service() {

    private lateinit var media:MediaPlayer


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        media=MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI)
        Log.d("MediaService","OnStart")
        media.isLooping=true

        media.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        media.stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }



}