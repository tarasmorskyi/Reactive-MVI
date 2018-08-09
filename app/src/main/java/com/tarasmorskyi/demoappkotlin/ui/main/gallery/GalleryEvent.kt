package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import paperparcel.PaperParcel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.SOURCE

@PaperParcel
data class GalleryEvent(@Events var event: Int = 0, var page: Page = Page()) : BaseEvent {

  companion object {
    @JvmField
    val CREATOR = PaperParcelGalleryEvent.CREATOR

    const val LOADED: Int = 1
    const val LIST_CLICKED: Int = 2
    const val LIKE: Int = 3

    fun onLoaded(): GalleryEvent {
      return GalleryEvent(LOADED)
    }

    fun onListClicked(page: Page): GalleryEvent {
      return GalleryEvent(LIST_CLICKED, page)
    }
    fun like(page: Page): GalleryEvent {
      return GalleryEvent(LIKE, page)
    }
  }

  @Retention(SOURCE)
  @IntDef(BaseEvent.NO_EVENT, LOADED, LIST_CLICKED, LIKE)
  internal annotation class Events
}