package com.tarasmorskyi.demoappkotlin.ui.main.settings

import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel


sealed class SettingsUiModel : BaseUiModel {

  data class Loaded(val searchSettings: SearchSettings) : SettingsUiModel()

  object Logout : SettingsUiModel()
}