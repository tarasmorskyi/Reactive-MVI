package com.tarasmorskyi.demoappkotlin.ui.splash

import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent

sealed class SplashEvent : BaseEvent {

  object Loaded : SplashEvent()
}