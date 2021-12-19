package com.example.firstapp.http

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer as OkioBuffer

class LoggingInterceptor:Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val time_start=System.nanoTime()
        val request =chain.request()
        val response=chain.proceed(request)

        //request 里面的body转为字符串
        val buffer= OkioBuffer()
        request.body().writeTo(buffer)
        val requestBodyStr=buffer.readUtf8()

        //打印请求
        Log.e("OKHTTP",String.format("Sending request %s with params %s",request.url(),requestBodyStr))

        //构建新的response
        val bussinessData=response.body().string()?:"response body null"//response.body().string()响应流，已经调用过了，外面不可以再调用了
        val mediaType=response.body().contentType()
        val newBody=ResponseBody.create(mediaType,bussinessData)
        val newResponse=response.newBuilder().body(newBody).build()

        val time_end=System.nanoTime()
        //打印响应
        Log.e("OKHTTP",String.format("Received response for %s in %.1fms >>> %s",request.url(),(time_end-time_start)/1e6,bussinessData))
        return newResponse
    }

}