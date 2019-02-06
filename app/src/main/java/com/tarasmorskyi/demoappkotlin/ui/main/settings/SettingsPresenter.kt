package com.tarasmorskyi.demoappkotlin.ui.main.settings

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.SettingsInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
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
    return events.flatMap { this.onEvent(it) }
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: SettingsEvent): ObservableSource<out SettingsUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      is SettingsEvent.Loaded -> interactor.settings.map { SettingsUiModel.Loaded(it) }.toObservable()
      is SettingsEvent.Logout -> interactor.logout().andThen(Observable.just(SettingsUiModel.Logout))
      is SettingsEvent.SaveSettings -> interactor.setSettings(event.searchSettings).andThen(Observable.empty())
    }
  }
}