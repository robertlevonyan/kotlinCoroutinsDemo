# Coroutines in Kotlin
Demo of how Coroutines in Kotlin work

Add following line of code to your module(app) level gradle file

```groovy
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:0.21"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:0.21"
```

### Starting a simple timer

Starting a timer with suspending <i>launch</i> function
```kotlin
    launch(UI) {
      for (i in 10 downTo 0) {
        timer.text = if (i == 0) "Time is out" else "$i"
        delay(1000)
        }
      }
```

And that's a result we are getting

<img src="https://github.com/robertlevonyan/kotlinCoroutinsDemo/blob/master/Images/kt_timer.jpg" width="300" />

### Requesting data from remote server with Retrofite

#### Exemple 1

```kotlin
    // Getting the weather for any city using suspending <i>async</i> function
    // The ```CommonPool``` is a thread where our coroutine should work
    fun getWeatherAsync(city: String): Deferred<Response<ResponseBody>> = async(CommonPool) {
        return@async api.getWeather("Yerevan", API_KEY).execute()
    }
    
    // Calling <i>getWeatherAsync</i> in a regular way in launch function, mentioning, that should work
    // in application's UI thread
    fun getWeather() {
        launch(UI) {
            val weather = getWeatherAsync("Yerevan")
            updateUI(weather.await())
        }
    }
```
