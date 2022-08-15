package com.gihandbook.my.domain

import androidx.lifecycle.MutableLiveData

class StateLiveData<T> : MutableLiveData<StateData<T>>() {

    fun postLoading() {
        postValue(StateData<T>().loading())
    }

    fun postError(throwable: Throwable) {
        postValue(StateData<T>().error(throwable))
    }

    fun postNotFound() {
        postValue(StateData<T>().notFound())
    }

    fun postComplete(data: T) {
        postValue(StateData<T>().complete(data))
    }
}