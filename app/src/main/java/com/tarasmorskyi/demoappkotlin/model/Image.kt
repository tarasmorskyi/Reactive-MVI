package com.tarasmorskyi.demoappkotlin.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable


@PaperParcel
data class Image(
    val link: String,
    val id: String
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelImage.CREATOR
  }
}