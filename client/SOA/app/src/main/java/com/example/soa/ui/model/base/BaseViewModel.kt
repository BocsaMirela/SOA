package com.example.soa.ui.model.base

import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

open class BaseViewModel : ViewModel() {

    private val clearedSubject = BehaviorSubject.create<Boolean>()

    protected val cleared: Flowable<Boolean> = clearedSubject.toFlowable(BackpressureStrategy.LATEST)

    override fun onCleared() {
        super.onCleared()
        clearedSubject.onNext(true)
        clearedSubject.onComplete()
    }
}