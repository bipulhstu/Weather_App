package com.bipulhstu.weatherapp.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ClientInstance @Inject constructor(){
    var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit? {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS) // connect timeout
            .writeTimeout(20, TimeUnit.SECONDS) // write timeout
            .readTimeout(20, TimeUnit.SECONDS) // read timeout
            .build()

        if (retrofit == null) {
            val BASE_URL = "https://api.openweathermap.org/"
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
        }
        return retrofit
    }
}