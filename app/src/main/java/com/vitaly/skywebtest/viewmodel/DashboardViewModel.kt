package com.vitaly.skywebtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vitaly.skywebtest.model.datasource.ImageDataSource
import com.vitaly.skywebtest.model.datasource.ImageDataSourceFactory
import com.vitaly.skywebtest.model.entity.ImageDataClass
import com.vitaly.skywebtest.model.networkservice.IImageRemoteDataSource
import com.vitaly.skywebtest.utils.State
import io.reactivex.rxjava3.disposables.CompositeDisposable


class DashboardViewModel(
    private val remoteDataSource: IImageRemoteDataSource
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val imageList: LiveData<PagedList<ImageDataClass>>
    private val imageDataSourceFactory: ImageDataSourceFactory
    private val pageSize = 5

    init {
        imageDataSourceFactory = createImageDataSourceFactory()
        val config = createPageListConfig()
        imageList = LivePagedListBuilder<Int, ImageDataClass>(imageDataSourceFactory, config)
            .build()

    }

    private fun createImageDataSourceFactory() =
        ImageDataSourceFactory(remoteDataSource, compositeDisposable)

    private fun createPageListConfig() = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize * 2)
        .setEnablePlaceholders(false)
        .build()

    fun retry() {
        imageDataSourceFactory.imageDataSourceLiveData.value?.retry()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<ImageDataSource,
            State>(imageDataSourceFactory.imageDataSourceLiveData, ImageDataSource::networkState)

    fun listIsEmpty(): Boolean {
        return imageList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}