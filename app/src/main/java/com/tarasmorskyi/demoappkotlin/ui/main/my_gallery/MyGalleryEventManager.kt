package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import android.annotation.SuppressLint
import com.tarasmorskyi.demoappkotlin.domain.interactors.MyGalleryInteractor
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEventManager
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject


internal class MyGalleryEventManager
@Inject constructor(
    private val interactor: MyGalleryInteractor) : BaseEventManager<MyGalleryEvent, MyGalleryUiModel>() {

  @SuppressLint("SwitchIntDef")
  override fun onEvent(event: MyGalleryEvent): ObservableSource<out MyGalleryUiModel> {
    Timber.d("event() called  with: event = [%s]", event)
    return when (event) {
      is MyGalleryEvent.Loaded -> interactor.posts.map { MyGalleryUiModel.Loaded(it) }.toObservable()
    }
  }
}