package com.vitaly.skywebtest.di


import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vitaly.skywebtest.R
import com.vitaly.skywebtest.model.datasource.WeatherDataSource
import com.vitaly.skywebtest.model.networkservice.IImageRemoteDataSource
import com.vitaly.skywebtest.model.networkservice.IWeatherRemoteDataSource
import com.vitaly.skywebtest.viewmodel.DashboardViewModel
import com.vitaly.skywebtest.viewmodel.HomeViewModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val photosModule = module {
    single(named("PHOTOS_BASE_URL")) { androidContext().getString(R.string.base_url_image) }
    single<Gson> {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
    single<IImageRemoteDataSource>(named("PhotosWebService")) {
        createWebService(
            okHttpClient = createPhotosOkHttpClient(),
            baseUrl = get(named("PHOTOS_BASE_URL")),
            gson = get()
        )
    }
    viewModel { DashboardViewModel(get(named("PhotosWebService"))) }
}

val authModule = module {
    single(named("base_url_weather")) { androidContext().getString(R.string.base_url_weather) }

    single<IWeatherRemoteDataSource>(named("WeatherWebService")) {

        createWebService(
            okHttpClient = createWeathersOkHttpClient(),
            baseUrl = get(named("base_url_weather")),
            gson = get()
        )
    }
    single { WeatherDataSource(get(named("WeatherWebService"))) }
    viewModel { HomeViewModel(get()) }
}

fun createPhotosOkHttpClient() = OkHttpClient.Builder().build()

fun createWeathersOkHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    return client.addInterceptor { chain ->
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("appid", "c35880b49ff95391b3a6d0edd0c722eb")
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


