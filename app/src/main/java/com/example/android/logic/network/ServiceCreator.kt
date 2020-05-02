package com.example.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//object 关键字将该类变为单例类
object ServiceCreator {
    private const val BASE_URL = "http://api.caiyunapp.com/"
    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serciceClass: Class<T>): T = retrofit.create(serciceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}