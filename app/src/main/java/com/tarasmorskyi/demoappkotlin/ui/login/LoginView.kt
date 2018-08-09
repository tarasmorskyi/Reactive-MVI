package com.tarasmorskyi.demoappkotlin.ui.login

import com.tarasmorskyi.demoappkotlin.ui.base.BaseView
import io.reactivex.Observer


internal interface LoginView : BaseView<LoginUiModel>, Observer<LoginUiModel>