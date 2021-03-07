package com.vitaly.skywebtest.model.entity.weather_rest

import com.google.gson.annotations.Expose

data class Main(
         val temp : Int,
         val humidity : Int
)