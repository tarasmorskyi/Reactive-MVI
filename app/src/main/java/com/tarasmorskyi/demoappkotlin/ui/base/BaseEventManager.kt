package com.tarasmorskyi.demoappkotlin.ui.base

import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.subjects.PublishSubject


abstract class BaseEventManager<E : BaseEvent, M : BaseUiModel> {
  protected val events: PublishSubject<E> = PublishSubject.create<E>()

  @CallSuper
  open fun attach(): Observable<M> {
    return events.flatMap { this.onEvent(it) }
  }

  @CallSuper
  fun event(event: E) {
    events.onNext(event)
  }

  protected abstract fun onEvent(event: E): ObservableSource<out M>
}
