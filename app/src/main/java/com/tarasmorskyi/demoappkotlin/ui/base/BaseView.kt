package com.tarasmorskyi.demoappkotlin.ui.base

interface BaseView<M : BaseUiModel> {
  fun render(uiModel: M)
}