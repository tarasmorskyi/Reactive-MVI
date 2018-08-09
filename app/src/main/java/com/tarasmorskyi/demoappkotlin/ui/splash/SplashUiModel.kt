package com.tarasmorskyi.demoappkotlin.ui.splash

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import paperparcel.PaperParcel

@PaperParcel
data class SplashUiModel(var model: Int = BaseUiModel.INVALID,
    var message: String = "") : BaseUiModel {

  companion object {
    @JvmField
    val CREATOR = PaperParcelSplashUiModel.CREATOR

    const val FAILURE: Int = 1
    const val GO_TO_MAIN: Int = 2
    const val GO_TO_LOGIN: Int = 3

    fun goToMain(): SplashUiModel {
      return SplashUiModel(GO_TO_MAIN)
    }

    fun goToLogin(): SplashUiModel {
      return SplashUiModel(GO_TO_LOGIN)
    }
  }

  @IntDef(BaseUiModel.INVALID, GO_TO_MAIN, GO_TO_LOGIN)
  internal annotation class Events
}