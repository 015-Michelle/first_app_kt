package com.example.firstapp.components

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class TestService2: Service() {
    private val TAG="TestService2"
    private var count=0
    private var quit=false

    private val binder=MyBinder()
    inner class MyBinder:Binder(){
        fun getCount():Int{
            return count
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate")

         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             val notification:Notification= Notification.Builder(applicationContext,"channel_id").build()
             startForeground(1,notification)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        Thread(Runnable {
            while (true){
                if(quit){
                    break
                }
                try{
                    Thread.sleep(1000)
                }catch (e:InterruptedException){
                    e.printStackTrace()
                }
                count++
            }
        }).start()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG,"onbind")
        return binder
    }

    override fun onDestroy() {
        Log.e(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG,"onUnbind")
        quit=true
        return super.onUnbind(intent)
    }
}