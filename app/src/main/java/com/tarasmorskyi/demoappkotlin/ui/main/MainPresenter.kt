package com.tarasmorskyi.demoappkotlin.ui.main

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.MainInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
import com.tarasmorskyi.demoappkotlin.utils.Constants
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

internal class MainPresenter
@Inject constructor(
    private val interactor: MainInteractor) : BasePresenter<MainView, MainEvent, MainUiModel>() {

  public override fun attach(view: MainView): Observable<MainUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<MainUiModel> {
    return events.flatMap({ this.onEvent(it) })
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: MainEvent): ObservableSource<MainUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    when (event.event) {
      MainEvent.LOADED -> return Observable.empty()
      else -> {
        Timber.e("event %s unhandled", event)
        return Observable.error(Constants.METHOD_NOT_IMPLEMENTED)
      }
    }
  }
}