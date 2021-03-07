package com.vitaly.skywebtest.utils

import com.google.gson.Gson
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun createPhotosOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

fun createWeathersOkHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    return client.addInterceptor { chain ->
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("appid", "c35880b49ff95391b3a6d0edd0c722eb")
            .addQueryParameter("id","553915")
            .addQueryParameter("lang","ru")
            .addQueryParameter("units","metric")
            .build()
        request = request.newBuilder()
            .url(url)
            .build()
        return@addInterceptor chain.proceed(request)
    }.build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String,
    gson: Gson
): T {

    val rFit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
    return rFit.create(T::class.java)

}