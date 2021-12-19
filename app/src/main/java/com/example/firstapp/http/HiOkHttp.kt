package com.example.firstapp.http

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.io.File

//class ：需要实例化之后才能调用方法
//object :可以直接 object名.方法
object HiOkHttp{

    private  val BASE_URL="http://123.56.232.18:8080/serverdemo"
    //初始化
    val client : OkHttpClient = OkHttpClient.Builder()//builder构造者设计模式
        .connectTimeout(10, TimeUnit.SECONDS)//链接超时时间
        .readTimeout(10,TimeUnit.SECONDS)//读取超时
        .writeTimeout(10,TimeUnit.SECONDS)//写超时，也就是请求超时
            .addInterceptor(HttpLoggingInterceptor())
        .build()

    //同步Get请求，一直等待http请求，直到返回了响应
    //所以同步请求不能在Android的主线程中执行，否则会报错NetworkMainThreadException

    //android分为主线程和子线程
    //主线程就是APP-启动后，Android framework层会启动一个线程，主线程(ui线程)
    //子线程-new Thread().start()
    fun get(){//网络请求接口
        //子线程中发起请求
        Thread(Runnable {
            //构造请求体
            val request: Request =Request.Builder()
                .url("$BASE_URL/user/query?userId=1600932269")
                .build()
            //构造请求对象
            val call: Call = client.newCall(request)
            //发起同步请求execute --同步执行
            val response: Response =call.execute()

            val body:String? =response.body().toString()

            Log.e("【OKHTTP】" ,"get response:${body}")
        }).start()
    }

    //get异步请求
    fun getAsync(){
        //构造请求体
        val request:Request =Request.Builder()
            .url("$BASE_URL/user/query?userId=1600932269")
            .build()
        //构造请求对象
        val call= client.newCall(request)
        //发起异步请求enqueue-异步执行，1000ms,1000ms
        val response=call.enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("【OKHTTP】" ,"get response onFailure:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body=response.body().toString()
                Log.e("【OKHTTP】" ,"get response:${body}")
            }
        } )
    }

    //post同步请求-表单提交
    fun post(){
        val body=FormBody.Builder()
                .add("userId","1600932269")
                .add("tagId","71")
                .build()
        val request=Request.Builder()
                .url("$BASE_URL/tag/toggleTagFollow")
                .post(body)
                .build()
        val call= client.newCall(request)

        Thread(Runnable {
            val response:Response=call.execute()
            Log.e("【OKHTTP】","post response:${response.body().string()}")
        }).start()

    }

    //post异步请求
    fun posyAsync(){
        val body=FormBody.Builder()
                .add("userId","1600932269")
                .add("tagId","71")
                .build()
        val request=Request.Builder()
                .url("$BASE_URL/tag/toggleTagFollow")
                .post(body)
                .build()
        val call=client.newCall(request)
        val response=call.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("【OKHTTP】","post onFailure:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("【OKHTTP】","post OnResponse:${response.body().string()}")
            }
        })
    }

    //post异步请求[多表单文件上传]
    fun postAsyncMultipart(context:Context){
        // 读取外部存储卡的文件，这里写法不完全【android6.0及以后，读取外部存储卡的文件都是需要动态申请权限的】
        val file = File(Environment.getExternalStorageDirectory(),"1.png")
        if(!file.exists()){
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show()
            return
        }
        val body=MultipartBody.Builder()
                .addFormDataPart("key","value")
                .addFormDataPart("key1","value1")
                .addFormDataPart("file","本地文件file.png", RequestBody.create(MediaType.parse("application/octet-stream"),file))
                .build()
        val request =Request.Builder()
                .url("接口是需要支持文件上传才可以使用的")
                .post(body)
                .build()
        val call=client.newCall(request)
        call.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("[OKHTTP]","postAsyncMultipart onFailure:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("[OKHTTP]","postAsyncMultipart onResponse:${response.body().string()}")
            }
        })
    }

    //异步post请求【提交字符串】
    fun postString(){
        val textPlain=MediaType.parse("text/plain;charset=utf-8")
        val textObj="username：username;password:password"

        val applicationJSON=MediaType.parse("application/json;charset:utf-8")
        val jsonObj=JSONObject()
        jsonObj.put("key1","value1")
        jsonObj.put("key2",100)

        val body=RequestBody.create(textPlain,textObj)//json格式的话，就传 application,jsonObj
        val request=Request.Builder()
                .url("$BASE_URL")
                .post(body)
                .build()
        val call= client.newCall(request)
        call.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
               Log.e("[OKHTTP]","postString onFailure:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("[OKHTTP]","postString onResponse:${response.body().string()}")
            }
        })
    }

}