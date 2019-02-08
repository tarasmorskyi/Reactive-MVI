package com.tarasmorskyi.demoappkotlin.ui.base

import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


abstract class  BaseEventManager<V : BaseView<*>, E : BaseEvent, M : BaseUiModel>   {
  protected val events : PublishSubject<E> = PublishSubject.create<E>()
  protected var view: V? = null

  @CallSuper
  protected open fun attach(view: V): Observable<M> {
    this.view = view
    return getModel()
  }

  @CallSuper
  protected open fun detach() {
    view = null
  }

  @CallSuper
  fun event(event: E): Observable<M> {
    events.onNext(event)
    return getModel()
  }

  protected abstract fun getModel(): Observable<M>

}
