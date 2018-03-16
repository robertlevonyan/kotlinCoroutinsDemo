package com.robertlevonyan.couroutinesdemo.api

import com.robertlevonyan.couroutinesdemo.API_KEY
import com.robertlevonyan.couroutinesdemo.Weather
import com.robertlevonyan.couroutinesdemo.await
import com.robertlevonyan.couroutinesdemo.getDataList
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.ResponseBody
import retrofit2.Response

object ApiManager {
    private val api = RetrofitClient.getClient().create(ApiService::class.java)

    fun getWeatherAsync(city: String): Deferred<Response<ResponseBody>> = async(CommonPool) {
        return@async api.getWeather("Yerevan", API_KEY).execute()
    }

    suspend fun getWeatherSuspending(city: String): Weather {
        return api.getWeather("Yerevan", API_KEY).await().getDataList(Array<Weather>::class.java)[0] as Weather
    }
}