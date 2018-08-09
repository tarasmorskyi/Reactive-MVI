package com.tarasmorskyi.demoappkotlin.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable
import java.util.Collections


@PaperParcel
data class Page(
    val id: String = "",
    val title: String = "",
    val link: String = "",
    val images: List<Image> = Collections.emptyList()
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelPage.CREATOR
  }
}