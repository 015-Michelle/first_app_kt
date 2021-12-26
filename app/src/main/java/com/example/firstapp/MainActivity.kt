package com.example.firstapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.components.SecondActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_study,
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        textview=TextView(this)
        textview.text="MainActivity"
        textview.gravity=Gravity.CENTER
        setContentView(textview)
        textview.setOnClickListener {
            //显式启动
            val intent=Intent(MainActivity@this, SecondActivity::class.java)
            intent.putExtra("haha","hello")
            intent.putExtra("h2",12)
            startActivityForResult(intent,100)

            //隐式启动
//            val intent=Intent()
//            intent.action="com.example.firstapp.action.SECONDACTIVITY"
//            intent.addCategory("com.example.firstapp.action.SECONDACTIVITY")
//            intent.putExtra("haha","hello")
//            intent.putExtra("h2",12)
//            startActivityForResult(intent,100)
//            //隐式启动-拨号
//            val uri=Uri.parse("tel:10086")
//            val intent=Intent(Intent.ACTION_DIAL,uri)
//            startActivity(intent)
//            //隐式启动-短信
//            val uri=Uri.parse("smsto:10086")
//            val intent=Intent(Intent.ACTION_SEND,uri)
//            intent.putExtra("sms_body","Hello")
//            startActivity(intent)


        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100&&resultCode== Activity.RESULT_OK&&data!=null){
            val strExtra=data.getStringExtra("result_text")
            val intExtra=data.getIntExtra("result_int",-1)
            textview.text="result ==>${strExtra} - ${intExtra}"
        }
    }
}