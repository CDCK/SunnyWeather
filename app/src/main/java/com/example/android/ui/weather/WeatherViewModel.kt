package com.example.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.logic.Repository
import com.example.android.logic.model.Location
/**
 * refreshWeather()方法来刷新天气信息方法，并将传入的经纬度参数封装成一个Location对象后赋值给locationLiveData对象，然后
 * 使用Transformations的switchMap()方法来观察这个对象,并在switchMap()方法的转换函数中调用仓库中定义的refreshWeather方法。这样仓库层
 * 返回的LiveData对象就可以转换成一个可供Activity观察的LiveData对象了
 */
class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()
    var locationLng=""
    var locationLat=""
    var placeName=""
    val weatherLiveData = Transformations.switchMap(locationLiveData){location->
        Repository.refreshWeather(location.lng,location.lat)
    }

    fun refreshWeather(lng :String,lat :String){
        locationLiveData.value= Location(lng, lat)
    }
}