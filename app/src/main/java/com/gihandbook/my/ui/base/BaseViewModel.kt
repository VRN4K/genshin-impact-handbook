package com.gihandbook.my.ui.base

import androidx.lifecycle.ViewModel
import com.gihandbook.my.domain.exceptions.createExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    protected val handler by lazy { createExceptionHandler(::onException) }

    open fun onException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}