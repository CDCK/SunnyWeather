package com.example.android.logic.network

import com.example.android.SunnyWeatherApplication
import com.example.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
//    http://api.caiyunapp.com/v2/place?query=%E5%8C%97%E4%BA%AC&token=LBCnK80RZUoBW3uM&kabg=zh_CN
//    @GET("v2/place?query={query}&token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}