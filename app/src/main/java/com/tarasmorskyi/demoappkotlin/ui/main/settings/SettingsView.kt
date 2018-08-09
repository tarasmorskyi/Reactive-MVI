package com.tarasmorskyi.demoappkotlin.ui.main.settings

import com.tarasmorskyi.demoappkotlin.ui.base.BaseView
import io.reactivex.Observer


internal interface SettingsView : BaseView<SettingsUiModel>, Observer<SettingsUiModel>