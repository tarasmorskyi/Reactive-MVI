package com.tarasmorskyi.demoappkotlin.ui.base

import paperparcel.PaperParcelable

interface BaseEvent : PaperParcelable {
  companion object {
    const val NO_EVENT : Int = 0
  }
}