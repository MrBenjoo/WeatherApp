package com.benji.weatherswe.utils.animation

import android.animation.ObjectAnimator
import android.view.View
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.airbnb.lottie.LottieAnimationView
import com.benji.weatherswe.R

class WeatherSymbolAnimator(view: View) {
    private var weatherSymbols: Array<LottieAnimationView>

    init {
        val symbolOne = view.findViewById<LottieAnimationView>(R.id.lottie_day_weather_1)
        val symbolTwo = view.findViewById<LottieAnimationView>(R.id.lottie_day_weather_2)
        val symbolThree = view.findViewById<LottieAnimationView>(R.id.lottie_day_weather_3)
        val symbolFour = view.findViewById<LottieAnimationView>(R.id.lottie_day_weather_4)
        val symbolFive = view.findViewById<LottieAnimationView>(R.id.lottie_day_weather_5)
        weatherSymbols = arrayOf(symbolOne, symbolTwo, symbolThree, symbolFour, symbolFive)
    }

    private fun animate(view: LottieAnimationView, delay: Long) {
        val animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -100f)
        with(animator) {
            interpolator = LinearOutSlowInInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1
            duration = 300
            startDelay = delay
            start()
        }
    }

    fun beginAnimation() {
        var delay = 150L
        for (symbol in weatherSymbols) {
            animate(symbol, delay)
            delay += 150
        }
    }
}