package com.vitaly.skywebtest.utils

import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

class Validation {
    private val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}\$"

    private val passwordPattern = Pattern.compile(PASSWORD_PATTERN)
    private val emailPattern = Pattern.compile(EMAIL_PATTERN)

    fun checkEmailIsNotBlank(
        email: String,
        emailIsNotBlankLiveData: MutableLiveData<Boolean>,
        validationEmailLiveData: MutableLiveData<Boolean>,
        checkValidShared: () -> Unit
    ) {
        if (email.isNotBlank()) {
            emailIsNotBlankLiveData.value = true
            emailValid(email, validationEmailLiveData, checkValidShared)
        } else
            emailIsNotBlankLiveData.value = false
    }

    fun checkPasswordIsNotBlank(
        password: String,
        passwordIsNotBlankLiveData: MutableLiveData<Boolean>,
        validationPasswordLiveData: MutableLiveData<Boolean>,
        checkValidShared: () -> Unit
    ) {
        if (password.isNotBlank()) {
            passwordIsNotBlankLiveData.value = true
            passwordValid(password, validationPasswordLiveData, checkValidShared)
        } else
            passwordIsNotBlankLiveData.value = false
    }

    private fun emailValid(
        email: String,
        validationEmailLiveData: MutableLiveData<Boolean>,
        checkValidShared: () -> Unit
    ) {
        validationEmailLiveData.value = emailPattern.matcher(email).matches()
        checkValidShared()
    }

    private fun passwordValid(
        password: String,
        validationPasswordLiveData: MutableLiveData<Boolean>,
        checkValidShared: () -> Unit
    ) {
        validationPasswordLiveData.value = passwordPattern.matcher(password).matches()
        checkValidShared()
    }
}