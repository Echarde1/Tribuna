package com.danildanil.rickmorty.presentation.base

import android.support.annotation.CallSuper
import com.danildanil.rickmorty.ui.base.MvpView
import com.danildanil.rickmorty.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {

    @Inject lateinit var newSchedulerProvider: SchedulerProvider
    @Inject lateinit var router: Router

    protected val view: V? get() = weakView.get()
    private var weakView: WeakReference<V?> = WeakReference(null)
    private val disposables = CompositeDisposable()

    @CallSuper
    override fun onAttach(view: V) {
        weakView = WeakReference(view)
        disposables.clear()
    }

    @CallSuper
    override fun onDetach() {
        weakView.clear()
        disposables.dispose()
    }

    override fun onBackCommandClick() {
        router.exit()
    }

    protected fun Disposable.addToPresenter(): Boolean = disposables.add(this)

    fun <T> Single<T>.subscribeOnIoToMain(): Single<T> =
        subscribeOn(newSchedulerProvider.io()).observeOn(newSchedulerProvider.ui())

    fun <T> Observable<T>.subscribeOnIoToMain(): Observable<T> =
        subscribeOn(newSchedulerProvider.io()).observeOn(newSchedulerProvider.ui())

    fun Completable.subscribeOnIoToMain(): Completable =
        subscribeOn(newSchedulerProvider.io()).observeOn(newSchedulerProvider.ui())
}