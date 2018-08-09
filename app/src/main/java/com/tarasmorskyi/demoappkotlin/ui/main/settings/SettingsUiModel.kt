package com.tarasmorskyi.demoappkotlin.ui.main.settings

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import paperparcel.PaperParcel


@PaperParcel
data class SettingsUiModel(var model: Int = BaseUiModel.INVALID, var message: String = "",
    var searchSettings: SearchSettings =
        SearchSettings())
  : BaseUiModel {

  companion object {
    @JvmField
    val CREATOR = PaperParcelSettingsUiModel.CREATOR

    const val FAILURE: Int = 1
    const val LOADED: Int = 2
    const val LOGOUT: Int = 3

    fun onFailure(message: String): SettingsUiModel {
      return SettingsUiModel(LOADED, message = message)
    }

    fun onLoaded(searchSettings: SearchSettings): SettingsUiModel {
      return SettingsUiModel(LOADED, searchSettings = searchSettings)
    }

    fun onLogout(): SettingsUiModel {
      return SettingsUiModel(LOGOUT)
    }
  }

  @IntDef(BaseUiModel.INVALID, LOADED, FAILURE, LOGOUT)
  internal annotation class Events
}