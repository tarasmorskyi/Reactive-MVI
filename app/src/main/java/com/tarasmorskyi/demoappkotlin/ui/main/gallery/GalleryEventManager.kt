package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.GalleryInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

internal class GalleryEventManager
@Inject constructor(
    private val interactor: GalleryInteractor) : BaseEventManager<GalleryView, GalleryEvent, GalleryUiModel>() {

  public override fun attach(view: GalleryView): Observable<GalleryUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<GalleryUiModel> {
    return events.flatMap { this.onEvent(it) }
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: GalleryEvent): ObservableSource<out GalleryUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {

      is GalleryEvent.Loaded -> interactor.posts.map { GalleryUiModel.Loaded(it) }.toObservable()

      is GalleryEvent.ListClicked -> Observable.just(GalleryUiModel.ShowLikeDialog(event.page))

      is GalleryEvent.Like -> interactor.like(event.page).andThen(Observable.empty())
    }
  }
}