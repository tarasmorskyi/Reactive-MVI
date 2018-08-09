package com.tarasmorskyi.demoappkotlin.ui.splash

import com.tarasmorskyi.demoappkotlin.ui.base.BaseView
import io.reactivex.Observer

internal interface SplashView : BaseView<SplashUiModel>, Observer<SplashUiModel>