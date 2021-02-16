package com.vitaly.skywebtest.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun injectDependencies() = loadModules
    private val loadModules by lazy {
        loadKoinModules(
            listOf( )
        )
    }

val remoteModule = module {

    val BASE_URL = "http://storyheroes.online/api/"

    single {
        OkHttpClient.Builder().build()
    }

    single {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    single {
        Retrofit.Builder()
            //.baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            //.build()
            //.create(IRemoteDataSource::class.java)
    }
}
