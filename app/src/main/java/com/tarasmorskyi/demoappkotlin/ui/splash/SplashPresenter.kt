package com.tarasmorskyi.demoappkotlin.ui.splash

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.SplashInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
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
    return events.flatMap { this.onEvent(it) }
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: SplashEvent): ObservableSource<out SplashUiModel> {
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