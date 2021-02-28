package com.vitaly.skywebtest.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vitaly.skywebtest.model.entity.ImageDataClass
import com.vitaly.skywebtest.model.networkservice.IImageRemoteDataSource
import com.vitaly.skywebtest.utils.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers

class ImageDataSource(
    private val remoteDataSource: IImageRemoteDataSource,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, ImageDataClass>() {

    var networkState: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageDataClass>
    ) {
        remoteDataSource.getImage(1, params.requestedLoadSize)
            .subscribe({ listPhotos ->
                updateState(State.DONE)
                callback.onResult(
                    listPhotos,
                    null,
                    2
                )
            }, {
                updateState(State.ERROR)
                setRetry(Action { loadInitial(params, callback) })
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageDataClass>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageDataClass>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            remoteDataSource.getImage(params.key, params.requestedLoadSize)
                .subscribe(
                    { listPhotos ->
                        updateState(State.DONE)
                        callback.onResult(
                            listPhotos,
                            params.key + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    private fun updateState(state: State) {
        networkState.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}