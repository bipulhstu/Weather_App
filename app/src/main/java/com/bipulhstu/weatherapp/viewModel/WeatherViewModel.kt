package com.bipulhstu.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bipulhstu.weatherapp.model.CityListResponse
import com.bipulhstu.weatherapp.model.CityWeatherResponse
import com.bipulhstu.weatherapp.retrofit.ApiConfig
import com.bipulhstu.weatherapp.retrofit.ClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WeatherViewModel @Inject constructor(): ViewModel() {

    val cityListLiveData = MutableLiveData<CityListResponse>()
    val cityWeatherLiveData = MutableLiveData<CityWeatherResponse>()

     fun getCityList(lat: Double, lon: Double, cnt: Int, appId: String) {
        val config = ClientInstance().getRetrofitInstance()!!.create(ApiConfig::class.java)
        val call: Call<CityListResponse> = config.getCityList(
            lat, lon, cnt, appId
        )
        call.enqueue(object : Callback<CityListResponse?> {
            override fun onResponse(
                call: Call<CityListResponse?>,
                response: Response<CityListResponse?>
            ) {
                if (response.isSuccessful) {
                    cityListLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<CityListResponse?>, t: Throwable) {
                Log.d("error", t.message!!)
            }
        })
    }

     fun getCityWeather(lat: Double, lon: Double, appId: String) {
        val config = ClientInstance().getRetrofitInstance()!!.create(ApiConfig::class.java)
        val call: Call<CityWeatherResponse> = config.getCityWeather(
            lat, lon, appId
        )
        call.enqueue(object : Callback<CityWeatherResponse?> {
            override fun onResponse(
                call: Call<CityWeatherResponse?>,
                response: Response<CityWeatherResponse?>
            ) {
                if (response.isSuccessful) {
                    cityWeatherLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<CityWeatherResponse?>, t: Throwable) {
                Log.d("error", t.message!!)
            }
        })
    }
}