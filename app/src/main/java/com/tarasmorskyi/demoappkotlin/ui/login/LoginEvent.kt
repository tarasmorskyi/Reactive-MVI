package com.tarasmorskyi.demoappkotlin.ui.login

import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent

sealed class LoginEvent : BaseEvent {
  data class Login(val userAuthenticationData: UserAuthenticationData) : LoginEvent()
}