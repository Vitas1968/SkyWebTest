package com.vitaly.skywebtest.model.datasource

import retrofit2.http.GET

interface IWeatherRemoteDataSource {
    @GET("weather?id=553915&lang=ru&units=metric")
    fun getWeather()
}