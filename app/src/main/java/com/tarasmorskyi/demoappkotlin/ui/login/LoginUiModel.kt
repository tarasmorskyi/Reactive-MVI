package com.tarasmorskyi.demoappkotlin.ui.login

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import paperparcel.PaperParcel

@PaperParcel
data class LoginUiModel(var model: Int = BaseUiModel.INVALID,
    var message: String = "") : BaseUiModel {

  companion object {
    @JvmField
    val CREATOR = PaperParcelLoginUiModel.CREATOR

    const val FAILURE: Int = 1
    const val GO_TO_SPLASH: Int = 2

    fun goToSplash(): LoginUiModel {
      return LoginUiModel(GO_TO_SPLASH)
    }
  }

  @IntDef(BaseUiModel.INVALID, GO_TO_SPLASH)
  internal annotation class Events
}