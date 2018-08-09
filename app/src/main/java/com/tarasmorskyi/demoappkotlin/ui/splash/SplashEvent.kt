package com.tarasmorskyi.demoappkotlin.ui.splash

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import paperparcel.PaperParcel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.SOURCE

@PaperParcel
data class SplashEvent(@Events var event: Int = 0) : BaseEvent {
  //constructor(event: Long) : this()

  companion object {
    @JvmField
    val CREATOR = PaperParcelSplashEvent.CREATOR

    const val LOADED: Int = 1

    fun onLoaded(): SplashEvent {
      return SplashEvent(LOADED)
    }
  }

  @Retention(SOURCE)
  @IntDef(BaseEvent.NO_EVENT, LOADED)
  internal annotation class Events
}