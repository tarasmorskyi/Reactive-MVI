package com.tarasmorskyi.demoappkotlin.ui.base

import paperparcel.PaperParcelable


interface BaseUiModel: PaperParcelable {
  companion object {
    const val INVALID : Int = -1

    fun shouldSave(): Boolean {return false}
    fun model(): Int {return INVALID}
  }
}