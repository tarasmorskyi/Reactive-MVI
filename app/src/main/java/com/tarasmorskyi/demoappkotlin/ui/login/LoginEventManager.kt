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
    private val interactor: LoginInteractor) : BaseEventManager<LoginView, LoginEvent, LoginUiModel>() {

  public override fun attach(view: LoginView): Observable<LoginUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<LoginUiModel> {
    return events.flatMap { this.onEvent(it) }
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: LoginEvent): ObservableSource<out LoginUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      is LoginEvent.Loaded -> Observable.empty()

      is LoginEvent.Login -> login(event.userAuthenticationData)
    }
  }

  private fun login(userAuthenticationData: UserAuthenticationData): Observable<out LoginUiModel> {
    return interactor.login(userAuthenticationData).andThen(Observable.just(GoToSplash))
  }
}