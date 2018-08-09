package com.tarasmorskyi.demoappkotlin.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class SearchSettings(
    var section: String = "hot",
    var sort: String = "viral",
    var window: String = "dau",
    var mature: Boolean = false,
    var showViral: Boolean = true
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelSearchSettings.CREATOR
  }
}