package com.vitaly.skywebtest.model.entity.weather

import com.google.gson.annotations.Expose

data class Weather(
        @Expose val id : Int,
        @Expose val main : String,
        @Expose val description : String,

)
