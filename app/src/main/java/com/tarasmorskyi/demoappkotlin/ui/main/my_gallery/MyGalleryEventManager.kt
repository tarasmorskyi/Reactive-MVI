package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.MyGalleryInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject


internal class MyGalleryEventManager
@Inject constructor(
    private val interactor: MyGalleryInteractor) : BaseEventManager<MyGalleryView, MyGalleryEvent, MyGalleryUiModel>() {

  public override fun attach(view: MyGalleryView): Observable<MyGalleryUiModel> {
    return super.attach(view)
    //method must be visible to package
  }

  public override fun detach() {
    super.detach()
    //method must be visible to package
  }

  override fun getModel(): Observable<MyGalleryUiModel> {
    return events.flatMap { this.onEvent(it) }
  }

  @SuppressLint("SwitchIntDef")
  private fun onEvent(event: MyGalleryEvent): ObservableSource<out MyGalleryUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      is MyGalleryEvent.Loaded -> interactor.posts.map { MyGalleryUiModel.Loaded(it) }.toObservable()
    }
  }
}