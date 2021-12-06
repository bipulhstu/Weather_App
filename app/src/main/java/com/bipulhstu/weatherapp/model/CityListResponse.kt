package com.bipulhstu.weatherapp.model

data class CityListResponse(
    val cod: String,
    val count: Int,
    val list: List<City>,
    val message: String
)