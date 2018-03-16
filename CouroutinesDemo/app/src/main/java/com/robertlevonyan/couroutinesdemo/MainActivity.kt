package com.robertlevonyan.couroutinesdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import com.robertlevonyan.couroutinesdemo.api.ApiManager.getWeatherSuspending
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import okhttp3.ResponseBody
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var isTimerOn = false
    private var timerJob: Job = Job()
    private var weatherJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        light()

        weatherProgress.setColor(R.color.colorDarkGray)

        checkWeather.setOnClickListener { getWeather() }
        startTimer.setOnClickListener { startTimer() }
        startChannel.setOnClickListener { showChannel() }
    }

    private fun getWeather() {
        weatherProgress.visibility = View.VISIBLE
        weatherJob = launch(UI) {

            //            val weather = ApiManager.getWeatherAsync("Yerevan")
//            updateUI(weather.await())

            val weather = getWeatherSuspending("Yerevan")
            updateUI(weather)
        }
    }

    private fun updateUI(weather: Response<ResponseBody>) {
        weatherProgress.visibility = View.GONE
        if (weather.isSuccessful) {
            if (weather.body() != null) {
                showIcons()
                val weatherBody = weather.body()?.getDataList(Array<Weather>::class.java)!![0] as Weather
                locationName.text = String.format("%s (%s, %s)", weatherBody.cityName, weatherBody.latitude, weatherBody.longitude)
                temp.text = Html.fromHtml(weatherBody.temp + " <sup>o</sup>C")
                humid.text = String.format("%s %%", weatherBody.humidity)
                wind.text = String.format("%s m/s, %s", weatherBody.windSpeed, weatherBody.windDir)
                sunrise.text = weatherBody.sunrise
                sunset.text = weatherBody.sunset
            }
        } else {
            temp.text = "Cannot get weather details"
        }
    }

    private fun updateUI(weather: Weather) {
        weatherProgress.visibility = View.GONE
        showIcons()
        locationName.text = String.format("%s (%s, %s)", weather.cityName, weather.latitude, weather.longitude)
        temp.text = Html.fromHtml(weather.temp + " <sup>o</sup>C")
        humid.text = String.format("%s %%", weather.humidity)
        wind.text = String.format("%s m/s, %s", weather.windSpeed, weather.windDir)
        sunrise.text = weather.sunrise
        sunset.text = weather.sunset
    }

    private fun showIcons() {
        locationIcon.visibility = View.VISIBLE
        humidIcon.visibility = View.VISIBLE
        windIcon.visibility = View.VISIBLE
        sunriseIcon.visibility = View.VISIBLE
        sunsetIcon.visibility = View.VISIBLE
    }

    private fun startTimer() {
        if (isTimerOn) {
            startTimer.text = getString(R.string.timer_start)
            timerJob.cancel()
        } else {
            startTimer.text = getString(R.string.timer_stop)
            timerJob = launch(UI) {
                for (i in 10 downTo 0) {
                    timer.text = if (i == 0) "Time is out" else "$i"
                    delay(1000)
                }
            }
        }
        isTimerOn = !isTimerOn
    }

    private fun showChannel() = async(UI) {
        val channel = Channel<Any>()
        launch(UI) {
            channel.send("My Channel")
            channel.send(15)
            channel.send(15F)
        }

        repeat(3) {
            delay(1000)
            launch(UI) {
                val channelValue = channel.receive()
                channelText.append(channelValue.toString() + " -> "
                        + channelValue::class.java.canonicalName + "\n")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!timerJob.isCancelled && !timerJob.isCompleted) {
            timerJob.cancel()
        }
        if (!weatherJob.isCancelled && !weatherJob.isCompleted) {
            weatherJob.cancel()
        }
    }
}
