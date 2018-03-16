package com.robertlevonyan.couroutinesdemo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
        @Expose
        @SerializedName("wind_cdir_full")
        val windDir: String,
        @Expose
        @SerializedName("lon")
        val longitude: String,
        @Expose
        @SerializedName("lat")
        val latitude: String,
        @Expose
        @SerializedName("wind_spd")
        val windSpeed: String,
        @Expose
        @SerializedName("sunrise")
        val sunrise: String,
        @Expose
        @SerializedName("sunset")
        val sunset: String,
        @Expose
        @SerializedName("temp")
        val temp: String,
        @Expose
        @SerializedName("city_name")
        val cityName: String,
        @Expose
        @SerializedName("rh")
        val humidity: String
)