package com.vitaly.skywebtest.model.networkservice


import com.vitaly.skywebtest.model.entity.ImageDataClass
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IImageRemoteDataSource {
    @GET("list")
    fun getImage(
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): Single<List<ImageDataClass>>
}