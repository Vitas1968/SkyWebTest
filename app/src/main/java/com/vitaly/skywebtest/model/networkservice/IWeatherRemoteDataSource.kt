package com.vitaly.skywebtest.model.networkservice

import com.vitaly.skywebtest.model.entity.weather_rest.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IWeatherRemoteDataSource {
    @GET("weather")
    fun getWeather(): Single<WeatherResponse>
}