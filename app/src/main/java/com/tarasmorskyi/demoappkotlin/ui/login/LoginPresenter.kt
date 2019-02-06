package com.tarasmorskyi.demoappkotlin.ui.login

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.LoginInteractor
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
import com.tarasmorskyi.demoappkotlin.utils.Constants
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

internal class LoginPresenter
@Inject constructor(
    private val interactor: LoginInteractor) : BasePresenter<LoginView, LoginEvent, LoginUiModel>() {

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
  private fun onEvent(event: LoginEvent): ObservableSource<LoginUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event.event) {
      LoginEvent.LOADED -> Observable.empty()

      LoginEvent.LOGIN -> login(event.userAuthenticationData)

      BaseEvent.NO_EVENT -> {
        Timber.e("event %s unhandled", event)
        Observable.error(Constants.METHOD_NOT_IMPLEMENTED)
      }

      else -> {
        Timber.e("event %s unhandled", event)
        Observable.error(Constants.METHOD_NOT_IMPLEMENTED)
      }
    }
  }

  private fun login(userAuthenticationData: UserAuthenticationData) : Observable<LoginUiModel> {
    return interactor.login(userAuthenticationData).andThen(Observable.just(LoginUiModel.goToSplash()))
  }
}