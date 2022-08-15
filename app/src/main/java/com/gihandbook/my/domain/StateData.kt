package com.gihandbook.my.domain

class StateData<T> {

    var status: DataStatus
    var data: T?
    var error: Throwable?

    init {
        status = DataStatus.LOADING
        data = null
        error = null
    }

    fun loading(): StateData<T> {
        status = DataStatus.LOADING
        data = null
        error = null
        return this
    }

    fun notFound(): StateData<T> {
        status = DataStatus.NOT_FOUND
        data = null
        error = null
        return this
    }

    fun error(error: Throwable): StateData<T> {
        status = DataStatus.ERROR
        data = null
        this.error = error
        return this
    }

    fun complete(data: T): StateData<T> {
        status = DataStatus.COMPLETE
        this.data = data
        error = null
        return this
    }

    enum class DataStatus {
        NOT_FOUND, ERROR, LOADING, COMPLETE
    }
}