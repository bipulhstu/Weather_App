package com.bipulhstu.weatherapp.retrofit

import com.bipulhstu.weatherapp.model.CityListResponse
import com.bipulhstu.weatherapp.model.CityWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiConfig {

    @GET("data/2.5/find")
    fun getCityList(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String
    ): Call<CityListResponse>

    @GET("data/2.5/weather")
    fun getCityWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Call<CityWeatherResponse>

}