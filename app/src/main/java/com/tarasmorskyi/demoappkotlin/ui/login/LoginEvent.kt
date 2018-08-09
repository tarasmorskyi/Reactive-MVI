package com.tarasmorskyi.demoappkotlin.ui.login

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import paperparcel.PaperParcel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.SOURCE

@PaperParcel
data class LoginEvent(@Events var event: Int = 0,
    var userAuthenticationData: UserAuthenticationData =
        UserAuthenticationData())
  : BaseEvent {
  //constructor(event: Long) : this()

  companion object {
    @JvmField
    val CREATOR = PaperParcelLoginEvent.CREATOR

    const val LOADED: Int = 1
    const val LOGIN: Int = 2

    fun onLoaded(): LoginEvent {
      return LoginEvent(LOADED)
    }

    fun onLogin(userAuthenticationData: UserAuthenticationData): LoginEvent {
      return LoginEvent(LOGIN, userAuthenticationData)
    }
  }

  @Retention(SOURCE)
  @IntDef(BaseEvent.NO_EVENT, LOADED, LOGIN)
  internal annotation class Events
}