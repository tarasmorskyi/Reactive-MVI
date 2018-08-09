package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.MyGalleryInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import com.tarasmorskyi.demoappkotlin.ui.base.BasePresenter
import com.tarasmorskyi.demoappkotlin.ui.main.my_gallery.MyGalleryEvent.Companion.LOADED
import com.tarasmorskyi.demoappkotlin.utils.Constants
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject


internal class MyGalleryPresenter
@Inject constructor(
    private val interactor: MyGalleryInteractor) : BasePresenter<MyGalleryView, MyGalleryEvent, MyGalleryUiModel>() {

  public override fun attach(view: MyGalleryView): Observable<MyGalleryUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<MyGalleryUiModel> {
    return events.flatMap({ this.onEvent(it) })
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: MyGalleryEvent): ObservableSource<MyGalleryUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    when (event.event) {
      LOADED -> return interactor.posts.map { MyGalleryUiModel.onLoaded(it) }.toObservable()
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