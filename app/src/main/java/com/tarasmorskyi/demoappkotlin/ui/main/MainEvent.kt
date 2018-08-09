package com.tarasmorskyi.demoappkotlin.ui.main

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import paperparcel.PaperParcel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.SOURCE


@PaperParcel
data class MainEvent(@Events var event: Int = 0,
    var page: Page = Page()) : BaseEvent {
  //constructor(event: Long) : this()

  companion object {
    @JvmField
    val CREATOR = PaperParcelMainEvent.CREATOR

    const val LOADED: Int = 1
    const val LIST_CLICKED: Int = 2

    fun onLoaded(): MainEvent {
      return MainEvent(LOADED)
    }

    fun onListClicked(page : Page): MainEvent {
      return MainEvent(LIST_CLICKED, page)
    }
  }

  @Retention(SOURCE)
  @IntDef(BaseEvent.NO_EVENT, LOADED, LIST_CLICKED)
  internal annotation class Events
}