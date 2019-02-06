package com.tarasmorskyi.demoappkotlin.ui.login

import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel

sealed class LoginUiModel : BaseUiModel {

  object GoToSplash : LoginUiModel()
}