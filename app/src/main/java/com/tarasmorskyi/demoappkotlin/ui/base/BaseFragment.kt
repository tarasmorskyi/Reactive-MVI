package com.tarasmorskyi.demoappkotlin.ui.base

import android.content.Context
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber


abstract class BaseFragment<E : BaseEvent, M : BaseUiModel> : RxFragment(), Observer<M>, BaseView<M> {

  //same reason as in BaseActivity
  /*protected final LifecycleProvider<Lifecycle.Event> provider =
      AndroidLifecycle.createLifecycleProvider(this);*/

  private val disposables = CompositeDisposable()

  @CallSuper
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  @CallSuper
  override fun onDetach() {
    super.onDetach()
    if (!disposables.isDisposed) {
      disposables.dispose()
    }
  }

  protected abstract fun sendEvent(event: E)

  override fun onSubscribe(d: Disposable) {
    disposables.add(d)
  }

  override fun onError(throwable: Throwable) {
    Timber.d(throwable, "onError() called")
  }

  override fun onComplete() { /*ignored*/
  }

  abstract override fun render(uiModel: M)

  fun <O> setDefaults(observable: Observable<O>): ObservableSource<O> {
    /*return observable.compose(provider.bindUntilEvent(Lifecycle.Event.ON_STOP))
        .observeOn(AndroidSchedulers.mainThread());*/
    return observable.compose(bindUntilEvent(FragmentEvent.STOP))
        .observeOn(AndroidSchedulers.mainThread())
  }
}
