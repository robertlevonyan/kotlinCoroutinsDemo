# Coroutines in Kotlin
Demo of how Coroutines in Kotlin work

Add following line of code to your module(app) level gradle file

```groovy
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:0.21"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:0.21"
```

Starting a simple timer using <i>launch</i> function
|
```kotlin
    launch(UI) {
      for (i in 10 downTo 0) {
        timer.text = if (i == 0) "Time is out" else "$i"
        delay(1000)
        }
      }
```
|<img src="https://github.com/robertlevonyan/kotlinCoroutinsDemo/blob/master/Images/kt_timer.jpg" width="500" />|
|----------------------------------------------------------------------------------------------|-----------|

And that's a result we are getting

