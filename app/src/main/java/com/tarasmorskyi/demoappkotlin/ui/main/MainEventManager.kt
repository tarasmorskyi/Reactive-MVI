package com.tarasmorskyi.demoappkotlin.ui.main

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.MainInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

internal class MainEventManager
@Inject constructor(
    private val interactor: MainInteractor) : BaseEventManager<MainEvent, MainUiModel>() {

  @SuppressLint("SwitchIntDef")
  override fun onEvent(event: MainEvent): ObservableSource<out MainUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return Observable.empty()
  }
}
