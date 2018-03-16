package com.robertlevonyan.couroutinesdemo

import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.google.gson.Gson
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun AppCompatActivity.light() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        window.decorView.rootView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR + View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark23)
    }
}

fun <T> ResponseBody.getDataList(clazz: Class<T>): List<*> {
    val responseString = this.string()
    val responseJson = JSONObject(responseString)

    if (responseJson.has("data")) {
        val dataObject = responseJson.getString("data")

        val result = Gson().fromJson(dataObject, clazz)
        return (result as Array<T>).asList()
    }

    return listOf<T>()
}

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            it.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                it.resume(response.body()!!)
            } else {
                it.resumeWithException(Throwable("Unsuccessful"))
            }
        }

    })
}

fun ProgressBar.setColor(colorRes: Int) {
    indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(context, colorRes),
            PorterDuff.Mode.SRC_IN)
}