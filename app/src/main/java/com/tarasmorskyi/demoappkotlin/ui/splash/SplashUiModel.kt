package com.tarasmorskyi.demoappkotlin.ui.splash

import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel

sealed class SplashUiModel : BaseUiModel {

  object GoToMain : SplashUiModel()

  object GoToLogin : SplashUiModel()
}