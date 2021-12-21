package com.example.firstapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.firstapp.http.ApiService
import com.example.firstapp.http.HiOkHttp
import com.example.firstapp.http.HiRetrofit
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_study,
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        HiOkHttp.get()
//        HiOkHttp.getAsync()
//        HiOkHttp.post()
//        HiOkHttp.posyAsync()
//        HiOkHttp.postString()

//        //onResponse和onFailure都是在主线程中的
//        val apiService=HiRetrofit.create(ApiService::class.java)
//        apiService.queryUser("1600932269").enqueue(object : Callback,
//            retrofit2.Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                Log.e("Retrofit",response.body()?:"response is null")
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.e("Retrofit",t.message?:"unknown reason")
//            }
//        })
    }
}