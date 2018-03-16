package com.robertlevonyan.couroutinesdemo.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current")
    fun getWeather(@Query("city") city: String, @Query("key") key: String): Call<ResponseBody>
}