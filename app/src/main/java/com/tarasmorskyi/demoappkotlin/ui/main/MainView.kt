package com.tarasmorskyi.demoappkotlin.ui.main

import com.tarasmorskyi.demoappkotlin.ui.base.BaseView
import io.reactivex.Observer

internal interface MainView : BaseView<MainUiModel>, Observer<MainUiModel>