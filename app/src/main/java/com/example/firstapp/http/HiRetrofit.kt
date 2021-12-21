package com.example.firstapp.http

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object HiRetrofit{
    private var client=OkHttpClient.Builder()
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .addInterceptor(LoggingInterceptor())
        .build()

    private var retrofit=Retrofit.Builder()
        .client(client)
        .baseUrl("https://www.songyubao.com/")
        .addConverterFactory(GsonConverterFactory.create())//数据转换适配器
        .build()

    fun <T> create(clazz: Class<T>):T{
        return retrofit.create(clazz)
    }
}

interface ApiService{
    @GET(value="user/query")
    fun queryUser(@Query(value = "userId",encoded = true) userId:String): Call<String>

    @GET(value = "static/book/assets/study.json")
    fun getStudy():Call<List<Course>>
}

//数据模型定义
data class Course(
    val label:String,
    val poster:String,
    var progress:String,
    val title:String
)


