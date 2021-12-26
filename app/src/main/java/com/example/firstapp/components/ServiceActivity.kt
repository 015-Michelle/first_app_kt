package com.example.firstapp.components

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.R
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity:AppCompatActivity() {
    private var myBinder:TestService2.MyBinder?=null
    private lateinit var connection:ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service)

        connection=object :ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                //service与activity连接时回调
                Log.e("SERVICEACTIVITY","---SERVICE CONNECT---")
                //这里的service（IBinder)就是TestService2中bind方法返回的binder对象
                myBinder=service as TestService2.MyBinder
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                //service与activity断开连接时回调
                Log.e("SERVICEACTIVITY","---SERVICE DISCONNECT---")
            }

        }

//        //运行一些和activity生命周期相等的后台任务，比如跨进程通信
//        val intent=Intent(this,TestService2::class.java)
//        bindService(intent, connection, Context.BIND_AUTO_CREATE)
        Handler().postDelayed(Runnable {
//            startService(Intent(this@ServiceActivity,TestService2::class.java))
           if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
               startForegroundService(Intent(this,TestService2::class.java))
           }else{
               //pre-0 behavior.
               startService(Intent(this,TestService2::class.java))
           }
        },70*1000)

        start_service.setOnClickListener {
//            val intent=Intent(this,TestService1::class.java)
//            startService(intent)
            Log.e("TestService2","getCount=${myBinder?.getCount()}")
        }
        stop_service.setOnClickListener {
//            val intent=Intent(this,TestService1::class.java)
//            stopService(intent)
            unbindService(connection)
        }
    }

    override fun onDestroy() {
        unbindService(connection!!)
        super.onDestroy()
    }
}