package com.bipulhstu.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class City(
    val clouds: @RawValue Clouds? = null,
    val coord: @RawValue Coord? = null,
    val dt: @RawValue Int? = null,
    val id: @RawValue Int? = null,
    val main: @RawValue Main? = null,
    val name: @RawValue String? = null,
    val rain: @RawValue Any? = null,
    val snow: @RawValue Any? = null,
    val sys: @RawValue Sys? = null,
    val weather: @RawValue List<Weather>? = null,
    val wind: @RawValue Wind? = null,
) : Parcelable