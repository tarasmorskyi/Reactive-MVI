package com.tarasmorskyi.demoappkotlin.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable


@PaperParcel
data class Verse(
    val meaning: String
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelVerse.CREATOR
  }
}