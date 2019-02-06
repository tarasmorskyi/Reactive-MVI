package com.tarasmorskyi.demoappkotlin.ui.main.settings

import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent

sealed class SettingsEvent : BaseEvent {

  object Loaded : SettingsEvent()

  object Logout : SettingsEvent()

  data class SaveSettings(val searchSettings: SearchSettings) : SettingsEvent()
}