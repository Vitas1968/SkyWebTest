package com.vitaly.skywebtest.model.entity.weather

import com.google.gson.annotations.Expose

data class WeatherResponse(
        @Expose val coord : Coord,
        @Expose val weather : Array<Weather>,
        @Expose val main : Main,
        @Expose val visibility : Int,
        @Expose val wind : Wind,
        @Expose val clouds : Clouds,
        @Expose val dt : Long,
        @Expose val sys : SysWeather,
        @Expose val timezone : Int,
        @Expose val id : Int,
        @Expose val name : String,
        @Expose val cod : Int

)
