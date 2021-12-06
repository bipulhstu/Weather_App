package com.bipulhstu.weatherapp.retrofit

import com.bipulhstu.weatherapp.model.CityListResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiConfig {

    //?lat=23.68&lon=90.35&cnt=50&appid=e384f9ac095b2109c751d95296f8ea76
    //@FormUrlEncoded
    @GET("data/2.5/find")
    suspend fun getCityList(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String
    ): Response<CityListResponse>


}