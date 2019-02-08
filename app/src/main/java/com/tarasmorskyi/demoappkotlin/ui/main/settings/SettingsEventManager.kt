package com.tarasmorskyi.demoappkotlin.ui.main.settings

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.SettingsInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject


internal class SettingsEventManager
@Inject constructor(
    private val interactor: SettingsInteractor) : BaseEventManager<SettingsEvent, SettingsUiModel>() {

  @SuppressLint("SwitchIntDef")
  override fun onEvent(event: SettingsEvent): ObservableSource<out SettingsUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      is SettingsEvent.Loaded -> interactor.settings.map { SettingsUiModel.Loaded(it) }.toObservable()
      is SettingsEvent.Logout -> interactor.logout().andThen(Observable.just(SettingsUiModel.Logout))
      is SettingsEvent.SaveSettings -> interactor.setSettings(event.searchSettings).andThen(Observable.empty())
    }
  }
}