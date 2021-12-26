package com.example.firstapp.components

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TestService1:Service() {
    private val TAG="TestService1"
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG,"onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.e(TAG,"onDestroy")
        super.onDestroy()
    }
}