package com.tarasmorskyi.demoappkotlin.ui.main.settings

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.SettingsInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
import com.tarasmorskyi.demoappkotlin.utils.Constants
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject


internal class SettingsPresenter
@Inject constructor(
    private val interactor: SettingsInteractor) : BasePresenter<SettingsView, SettingsEvent, SettingsUiModel>() {

  public override fun attach(view: SettingsView): Observable<SettingsUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<SettingsUiModel> {
    return events.flatMap({ this.onEvent(it) })
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: SettingsEvent): ObservableSource<SettingsUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    when (event.event) {
      SettingsEvent.LOADED -> return interactor.settings.map { SettingsUiModel.onLoaded(it) }.toObservable()
      SettingsEvent.LOGOUT -> return interactor.logout().andThen(Observable.just(SettingsUiModel.onLogout()))
      SettingsEvent.SAVE_SETTINGS -> return interactor.setSettings(event.searchSettings).andThen(Observable.empty())
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
}