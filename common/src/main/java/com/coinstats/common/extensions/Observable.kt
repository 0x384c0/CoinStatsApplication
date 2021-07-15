package com.coinstats.common.extensions


import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * subscribe On io thread
 * observe On main Thread
 */
@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T> Observable<T>.subscribeMain(
    onNext: ((T) -> Unit),
    onError: ((Throwable) -> Unit)? = null,
    onComplete: ((Unit) -> Unit)? = null
): Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread(), true)
        .subscribe(onNext, { onError?.invoke(it) }, { onComplete?.invoke(Unit) })
}


/**
 * subscribe On io thread
 * observe On main Thread
 */
@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun Completable.subscribeMain(
    onNext: (() -> Unit),
    onError: ((Throwable) -> Unit)? = null
): Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNext, { onError?.invoke(it) })
}

fun Disposable.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

