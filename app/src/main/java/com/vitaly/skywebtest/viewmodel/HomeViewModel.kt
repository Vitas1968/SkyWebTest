package com.vitaly.skywebtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaly.skywebtest.model.datasource.WeatherDataSource
import com.vitaly.skywebtest.model.entity.WeatherReady
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.regex.Pattern


class HomeViewModel(
    private val weatherDataSource: WeatherDataSource
) : ViewModel() {
    private val weatherReadyLiveData = MutableLiveData<WeatherReady>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val progressLiveData = MutableLiveData<Boolean>()
    private val validationEmailLiveData = MutableLiveData<Boolean>()
    private val validationPasswordLiveData = MutableLiveData<Boolean>()
    private val validationSharedLiveData = MutableLiveData<Boolean>()
    private val validation: Validation = Validation()

    init {
        validationSharedLiveData.value = false
    }

    fun subscribeOnWeatherReady(): LiveData<WeatherReady> = weatherReadyLiveData

    fun subscribeOnProgress(): LiveData<Boolean> = progressLiveData

    fun subscribeOnValidEmail(): LiveData<Boolean> = validationEmailLiveData

    fun subscribeOnValidPassword(): LiveData<Boolean> = validationPasswordLiveData

    fun subscribeOnValidShared(): LiveData<Boolean> = validationSharedLiveData

    fun subscribeOnError(): LiveData<Throwable> = errorLiveData


    fun checkValidEmail(email: String) {
        validationEmailLiveData.value = validation.emailValid(email)
        checkValidShared()
    }

    fun checkValidPassword(password: String) {
        validationPasswordLiveData.value = validation.passwordValid(password)
        checkValidShared()
    }

    private fun checkValidShared() {
        if (validationEmailLiveData.value != null &&
            validationPasswordLiveData.value != null &&
            validationEmailLiveData.value == true &&
            validationPasswordLiveData.value == true
        ) getWeather()
        else validationSharedLiveData.value = false
    }

    private fun getWeather() {
        progressLiveData.value = true
        weatherDataSource.getWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressLiveData.value = false
                weatherReadyLiveData.value = it
                validationSharedLiveData.value = true
            }, {
                errorLiveData.value = it
            })
    }

    private class Validation {
        private val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}\$"

        fun emailValid(email: String): Boolean {
            val pattern = Pattern.compile(EMAIL_PATTERN)
            return pattern.matcher(email).matches()
        }

        fun passwordValid(password: String): Boolean {
            val pattern = Pattern.compile(PASSWORD_PATTERN)
            return pattern.matcher(password).matches()
        }
    }
}