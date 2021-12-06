package com.bipulhstu.weatherapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bipulhstu.weatherapp.model.CityListResponse
import com.bipulhstu.weatherapp.retrofit.ApiConfig

class CityListRepository(private val apiConfig: ApiConfig) {

    private val cityListLiveData = MutableLiveData<CityListResponse>()

    val cityList: LiveData<CityListResponse>
        get() = cityListLiveData

    suspend fun getCityList(
        lat: Double,
        lon: Double,
        cnt: Int,
        appid: String
    ) {
        val result = apiConfig.getCityList(lat, lon, cnt, appid)
        if (result.body() != null) {
            cityListLiveData.postValue(result.body())
        }
    }
}