package com.vitaly.skywebtest.model.datasource

import com.vitaly.skywebtest.model.entity.WeatherReady
import com.vitaly.skywebtest.model.networkservice.IWeatherRemoteDataSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherDataSource(
    private val weatherWebService: IWeatherRemoteDataSource
) {
    fun getWeather(): Single<WeatherReady> =
        weatherWebService.getWeather()
            .flatMap {
                val weatherReady = WeatherReady(
                    cityName = it.name,
                    cityId = it.id,
                    temperature = it.main.temp,
                    humidity = it.main.humidity,
                    description = it.weather[0].description
                )
                Single.just(weatherReady)
            }.subscribeOn(Schedulers.io())


}