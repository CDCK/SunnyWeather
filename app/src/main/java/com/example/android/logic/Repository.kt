package com.example.android.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.example.android.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import com.example.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
            val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
    }

    /**
     * 获取实时天气信息和获取未来天气信息这两个请求没有先后顺序，因此让它们并发执行可以提升运行效率，所以就使用两个async函数发起网络请求，然后再分别调用它们的await()方法，
     * 就可以保证只有在两个网络请求都成功响应后才会进一步执行程序。另外，由于async函数必须在协程作用域内才能调用，所以需要使用coroutineScope函数创建一个协程作用域。
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
            //协程作用域
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetWork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    //包装weather对象
                    Result.success(weather)
                } else {
                    //包装一个异常信息
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}  daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
    }

    private fun <T> fire(context: CoroutineContext, block :suspend()-> Result<T>) =
        liveData<Result<T>>(context){
            val result = try {
                block()
            }catch (e:Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }

}