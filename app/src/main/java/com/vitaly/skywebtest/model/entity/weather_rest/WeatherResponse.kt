package com.vitaly.skywebtest.model.entity.weather_rest

import com.google.gson.annotations.Expose

data class WeatherResponse(
        @Expose val weather : Array<Weather>,
        @Expose val main : Main,
        @Expose val id : Int,
        @Expose val name : String,
)
