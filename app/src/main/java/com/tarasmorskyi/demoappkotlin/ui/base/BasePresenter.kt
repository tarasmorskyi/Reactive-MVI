package com.tarasmorskyi.demoappkotlin.ui.base

import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


abstract class  BasePresenter<V : BaseView<*>, E : BaseEvent, M : BaseUiModel>   {
  protected val events : PublishSubject<E> = PublishSubject.create<E>()
  protected var view: V? = null

  @CallSuper
  open protected fun attach(view: V): Observable<M> {
    this.view = view
    return getModel()
  }

  @CallSuper
  open protected fun detach() {
    view = null
  }

  @CallSuper
  public fun event(event: E): Observable<M> {
    events.onNext(event)
    return getModel()
  }

  protected abstract fun getModel(): Observable<M>

}
