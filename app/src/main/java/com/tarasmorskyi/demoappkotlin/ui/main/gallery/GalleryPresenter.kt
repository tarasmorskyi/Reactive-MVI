package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.GalleryInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryEvent.Companion.LIKE
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryEvent.Companion.LIST_CLICKED
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryEvent.Companion.LOADED
import com.tarasmorskyi.demoappkotlin.utils.Constants
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

internal class GalleryPresenter
@Inject constructor(
    private val interactor: GalleryInteractor) : BasePresenter<GalleryView, GalleryEvent, GalleryUiModel>() {

  public override fun attach(view: GalleryView): Observable<GalleryUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<GalleryUiModel> {
    return events.flatMap({ this.onEvent(it) })
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: GalleryEvent): ObservableSource<GalleryUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event.event) {
      LOADED -> interactor.posts.map { GalleryUiModel.onLoaded(it) }.toObservable()
      LIKE -> interactor.like(event.page).andThen(Observable.empty())
      LIST_CLICKED -> Observable.just(GalleryUiModel.onShowLikeDialog(event.page))
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
}