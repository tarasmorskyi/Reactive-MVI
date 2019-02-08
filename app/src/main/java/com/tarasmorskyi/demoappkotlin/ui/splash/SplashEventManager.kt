package com.tarasmorskyi.demoappkotlin.ui.splash

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.SplashInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

internal class SplashEventManager
@Inject constructor(
    private val interactor: SplashInteractor) : BaseEventManager<SplashEvent, SplashUiModel>() {

  public override fun attach(): Observable<SplashUiModel> {
    return super.attach()
    //method must be visible to package
  }

  @SuppressLint("SwitchIntDef")
  override fun onEvent(event: SplashEvent): ObservableSource<out SplashUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      SplashEvent.Loaded -> isLoggedIn()
    }
  }

  private fun isLoggedIn(): Observable<out SplashUiModel> {
    return interactor.isLoggedIn().map { isLoggedIn ->
      if (isLoggedIn) SplashUiModel.GoToMain
      else SplashUiModel.GoToLogin
    }.toObservable().delay(2, SECONDS)
  }
}