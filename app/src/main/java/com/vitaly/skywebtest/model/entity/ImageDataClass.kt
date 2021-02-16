package com.vitaly.skywebtest.model.entity

import com.google.gson.annotations.Expose

data class ImageDataClass(
    @Expose val id : Long,
    @Expose val author : String,
    @Expose val width : Int,
    @Expose val height : Int,
    @Expose val url : String,
    @Expose val download_url : String
    )
