package com.wordbuzzer.domain.observer

import io.reactivex.observers.DisposableSingleObserver

abstract class SingleObserver<T> : DisposableSingleObserver<T>() {

    abstract fun error(e: Throwable?)
    abstract fun success(t: T?)

    override fun onSuccess(t: T) {
        success(t)
    }

    override fun onError(e: Throwable) {
        error(e)
    }

}