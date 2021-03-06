package com.vitaly.skywebtest.model.entity.weather_rest


data class WeatherResponse(
         val weather : Array<Weather>,
         val main : Main,
         val name : String,
)
