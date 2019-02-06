package com.tarasmorskyi.demoappkotlin.ui.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import com.tarasmorskyi.demoappkotlin.App
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


@SuppressLint("Registered")
abstract class BaseActivity<M : BaseUiModel, E : BaseEvent>
  : DaggerAppCompatActivity(), Observer<M>, BaseView<M>, HasSupportFragmentInjector {

  protected var disposables = CompositeDisposable()
  protected var progressDialog: ProgressDialog? = null
  @Inject
  internal lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
  @Inject
  internal lateinit var appInstance: App

  override fun onCreate(savedInstanceState: Bundle?) {
    var savedInstanceStateFinal = savedInstanceState
    if (savedInstanceState == null) savedInstanceStateFinal = Bundle.EMPTY
    super.onCreate(savedInstanceStateFinal)
  }

  public override fun onSaveInstanceState(outState: Bundle) {
    try {
      super.onSaveInstanceState(outState)
    } catch (ignore: IllegalStateException) {
    }
  }

  override fun onStop() {
    Timber.d("onStop: []")
    super.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    if (!disposables.isDisposed) {
      disposables.dispose()
    }
    if (progressDialog != null) {
      progressDialog!!.dismiss()
    }
  }

  @CallSuper
  override fun onSubscribe(d: Disposable) {
    Timber.d("onSubscribe() called  with: disposable = [%s]", d)
    disposables.add(d)
  }

  /**
   * Override to catch errors on events
   */
  override fun onError(throwable: Throwable) {
    Timber.d("onError: [throwable]")
    hideProgress()
  }

  /**
   * Override to catch onComplete event from events
   */
  override fun onComplete() {
    Timber.d("onComplete: []")
    /*ignored*/
  }

  abstract override fun render(uiModel: M)

  override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
    return fragmentInjector
  }

  /**
   * Override to send events to presenter. For the most cases use inside:
   * presenter.event(...).compose(this::setDefaults).subscribe(this);
   * that will ensure m\odels are observed on the Main Thread
   * and subscriptions are held until ON_PAUSE events
   */
  protected abstract fun sendEvent(event: E)

  fun <T> setDefaults(observable: Observable<T>): ObservableSource<T> {
    val converted = observable
    return observable.observeOn(AndroidSchedulers.mainThread())
  }

  fun showProgress(message: String) {
    if (progressDialog == null) {
      progressDialog = ProgressDialog(this)
      progressDialog!!.setCancelable(false)
    }
    progressDialog!!.setMessage(message)
    progressDialog!!.show()
  }

  fun hideProgress() {
    if (progressDialog != null) {
      progressDialog!!.dismiss()
    }
  }

}