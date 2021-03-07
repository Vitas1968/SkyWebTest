package com.vitaly.skywebtest.model.entity

data class WeatherReady(
        val cityName: String = "No city",
        val temperature: Int,
        val humidity: Int,
        val description: String = "No description"
)
