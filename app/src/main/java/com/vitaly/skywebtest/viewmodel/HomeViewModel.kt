package com.vitaly.skywebtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaly.skywebtest.model.datasource.WeatherDataSource
import com.vitaly.skywebtest.model.entity.WeatherReady
import com.vitaly.skywebtest.utils.Validation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class HomeViewModel(
    private val weatherDataSource: WeatherDataSource
) : ViewModel() {
    private val weatherReadyLiveData = MutableLiveData<WeatherReady>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val progressLiveData = MutableLiveData<Boolean>()
    private val validationEmailLiveData = MutableLiveData<Boolean>()
    private val validationPasswordLiveData = MutableLiveData<Boolean>()
    private val validationSharedLiveData = MutableLiveData<Boolean>()
    private val emailIsNotBlankLiveData = MutableLiveData<Boolean>()
    private val passwordIsNotBlankLiveData = MutableLiveData<Boolean>()
    private val validation: Validation by lazy { Validation() }

    init {
        validationSharedLiveData.value = false
    }

    fun subscribeOnWeatherReady(): LiveData<WeatherReady> = weatherReadyLiveData

    fun subscribeOnEmailIsNotBlank(): LiveData<Boolean> = emailIsNotBlankLiveData

    fun subscribeOnPasswordIsNotBlank(): LiveData<Boolean> = passwordIsNotBlankLiveData

    fun subscribeOnProgress(): LiveData<Boolean> = progressLiveData

    fun subscribeOnValidEmail(): LiveData<Boolean> = validationEmailLiveData

    fun subscribeOnValidPassword(): LiveData<Boolean> = validationPasswordLiveData

    fun subscribeOnValidShared(): LiveData<Boolean> = validationSharedLiveData

    fun subscribeOnError(): LiveData<Throwable> = errorLiveData

    fun emailValid(email: String) {
        validation.checkEmailIsNotBlank(
            email,
            emailIsNotBlankLiveData,
            validationEmailLiveData
        ) { checkValidShared() }
    }

    fun passwordValid(password: String) {
        validation.checkPasswordIsNotBlank(
            password,
            passwordIsNotBlankLiveData,
            validationPasswordLiveData
        ) { checkValidShared() }
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
}