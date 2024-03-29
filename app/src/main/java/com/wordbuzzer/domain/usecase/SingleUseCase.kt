package com.wordbuzzer.domain.usecase

import com.wordbuzzer.domain.thread.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


abstract class SingleUseCase<T, in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {

    protected val PARAMS_NOT_NULL = "params cannot be null"

    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    open fun execute(observer: DisposableSingleObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}