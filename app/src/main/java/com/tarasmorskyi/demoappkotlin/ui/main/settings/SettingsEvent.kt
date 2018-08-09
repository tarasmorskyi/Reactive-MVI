package com.tarasmorskyi.demoappkotlin.ui.main.settings

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent
import paperparcel.PaperParcel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.SOURCE

@PaperParcel
data class SettingsEvent(@Events var event: Int = 0,
    var searchSettings: SearchSettings =
        SearchSettings()) : BaseEvent {
  //constructor(event: Long) : this()

  companion object {
    @JvmField
    val CREATOR = PaperParcelSettingsEvent.CREATOR

    const val LOADED: Int = 1
    const val SAVE_SETTINGS: Int = 2
    const val LOGOUT: Int = 3

    fun onLoaded(): SettingsEvent {
      return SettingsEvent(LOADED)
    }

    fun onLogout(): SettingsEvent {
      return SettingsEvent(LOGOUT)
    }

    fun saveSettings(searchSettings: SearchSettings): SettingsEvent {
      return SettingsEvent(SAVE_SETTINGS, searchSettings)
    }
  }

  @Retention(SOURCE)
  @IntDef(BaseEvent.NO_EVENT, LOADED, SAVE_SETTINGS, LOGOUT)
  internal annotation class Events
}