package com.vitaly.skywebtest.di


import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vitaly.skywebtest.R
import com.vitaly.skywebtest.model.datasource.ImageDataSourceFactory
import com.vitaly.skywebtest.model.datasource.WeatherDataSource
import com.vitaly.skywebtest.model.networkservice.IImageRemoteDataSource
import com.vitaly.skywebtest.model.networkservice.IWeatherRemoteDataSource
import com.vitaly.skywebtest.utils.createPageListConfig
import com.vitaly.skywebtest.utils.createPhotosOkHttpClient
import com.vitaly.skywebtest.utils.createWeathersOkHttpClient
import com.vitaly.skywebtest.utils.createWebService
import com.vitaly.skywebtest.viewmodel.DashboardViewModel
import com.vitaly.skywebtest.viewmodel.HomeViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependenciesIntoDashBoard() = loadPhotos

val loadPhotos by lazy { loadKoinModules(photosModule) }

fun injectDependenciesIntoHome() = loadAuth

private val loadAuth by lazy { loadKoinModules(authModule) }

val appModule = module {
    single<Gson> {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }
}

val photosModule = module {
    single(named("DashboardComposite")) { CompositeDisposable() }
    single(named("PHOTOS_BASE_URL")) { androidContext().getString(R.string.base_url_image) }
    single<IImageRemoteDataSource>(named("PhotosWebService")) {
        createWebService(
            okHttpClient = createPhotosOkHttpClient(),
            baseUrl = get(named("PHOTOS_BASE_URL")),
            gson = get()
        )
    }
    single(named("ImgDataSourceFactory")) {
        ImageDataSourceFactory(
            get(named("PhotosWebService")),
            get(named("DashboardComposite"))
        )
    }
    single(named("PagedListConfig")) { createPageListConfig() }

    viewModel {
        DashboardViewModel(
            get(named("DashboardComposite")),
            get(named("ImgDataSourceFactory")),
            get(named("PagedListConfig"))
        )
    }
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







