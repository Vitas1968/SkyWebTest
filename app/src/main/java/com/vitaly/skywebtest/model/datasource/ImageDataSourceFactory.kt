package com.vitaly.skywebtest.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.vitaly.skywebtest.model.entity.ImageDataClass
import com.vitaly.skywebtest.model.networkservice.IImageRemoteDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ImageDataSourceFactory(
    private val remoteDataSource: IImageRemoteDataSource,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, ImageDataClass>() {

    val imageDataSourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, ImageDataClass> {
        val imgDataSource = ImageDataSource(remoteDataSource, compositeDisposable)
        imageDataSourceLiveData.postValue(imgDataSource)
        return imgDataSource
    }
}