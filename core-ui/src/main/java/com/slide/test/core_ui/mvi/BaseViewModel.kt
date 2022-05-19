package com.slide.test.core_ui.mvi

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Created by Stefan Halus on 18 May 2022
 */
abstract class BaseViewModel<A : BaseAction, S : BaseState> : ViewModel() {

    protected val actions: PublishSubject<A> = PublishSubject.create<A>()

    protected val disposables: CompositeDisposable = CompositeDisposable()

    abstract val initialState: S

    protected val state = BehaviorRelay.create<S>()

    private val tag by lazy { javaClass.simpleName }

    /**
     * Returns the current state. It is equal to the last value returned by the store's reducer.
     */
    val observableState: Observable<S> = state

    /**
     * Dispatches an action. This is the only way to trigger a state change.
     */
    fun dispatch(action: A) {
        actions.onNext(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}