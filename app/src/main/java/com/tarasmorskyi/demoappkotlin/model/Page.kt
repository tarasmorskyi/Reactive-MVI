package com.tarasmorskyi.demoappkotlin.model

import com.tarasmorskyi.demoappkotlin.utils.Constants.EMPTY_STRING
import paperparcel.PaperParcel
import paperparcel.PaperParcelable
import java.util.Collections


@PaperParcel
data class Page(
    val id: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val link: String = EMPTY_STRING,
    val images: List<Image> = Collections.emptyList()
) : PaperParcelable {
  companion object {
    @JvmField
    val CREATOR = PaperParcelPage.CREATOR
  }
}