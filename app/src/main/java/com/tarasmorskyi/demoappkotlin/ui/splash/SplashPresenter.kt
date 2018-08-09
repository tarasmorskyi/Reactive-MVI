package com.tarasmorskyi.demoappkotlin.ui.splash

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.SplashInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
import com.tarasmorskyi.demoappkotlin.utils.Constants
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

internal class SplashPresenter
@Inject constructor(
    private val interactor: SplashInteractor) : BasePresenter<SplashView, SplashEvent, SplashUiModel>() {

  public override fun attach(view: SplashView): Observable<SplashUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<SplashUiModel> {
    return events.flatMap({ this.onEvent(it) })
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: SplashEvent): ObservableSource<SplashUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    when (event.event) {
      SplashEvent.LOADED -> return isLoggedIn()
      BaseEvent.NO_EVENT -> {
        Timber.e("event %s unhandled", event)
        return Observable.error(Constants.METHOD_NOT_IMPLEMENTED)
      }
      else -> {
        Timber.e("event %s unhandled", event)
        return Observable.error(Constants.METHOD_NOT_IMPLEMENTED)
      }
    }
  }

  private fun isLoggedIn(): Observable<SplashUiModel> {
    return interactor.isLoggedIn().map({ isLoggedIn ->
          if (isLoggedIn) SplashUiModel.Companion.goToMain()
          else SplashUiModel.Companion.goToLogin() }).toObservable().delay(2, SECONDS)
  }
}