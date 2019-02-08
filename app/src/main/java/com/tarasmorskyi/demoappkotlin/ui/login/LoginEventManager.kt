package com.tarasmorskyi.demoappkotlin.ui.login

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.LoginInteractor
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import com.tarasmorskyi.demoappkotlin.ui.login.LoginUiModel.GoToSplash
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

internal class LoginEventManager
@Inject constructor(
    private val interactor: LoginInteractor) : BaseEventManager<LoginEvent, LoginUiModel>() {

  @SuppressLint("SwitchIntDef")
  override fun onEvent(event: LoginEvent): ObservableSource<out LoginUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      is LoginEvent.Login -> interactor.login(event.userAuthenticationData).andThen(Observable.just(GoToSplash))
    }
  }
}