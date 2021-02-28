package com.vitaly.skywebtest.model.entity.weather_rest

import com.google.gson.annotations.Expose

data class Main(
        @Expose val temp : Int,
        @Expose val feels_like : Float,
        @Expose val temp_min : Int,
        @Expose val temp_max : Int,
        @Expose val pressure : Int,
        @Expose val humidity : Int
)