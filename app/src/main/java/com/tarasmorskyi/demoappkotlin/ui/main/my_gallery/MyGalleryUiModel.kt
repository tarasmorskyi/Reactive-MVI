package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import paperparcel.PaperParcel
import java.util.Collections


@PaperParcel
data class MyGalleryUiModel(var model: Int = BaseUiModel.INVALID, var message: String = "",
    var pages: List<Page> = Collections.emptyList()) : BaseUiModel {

  companion object {
    @JvmField
    val CREATOR = PaperParcelMyGalleryUiModel.CREATOR

    const val FAILURE: Int = 1
    const val LOADED: Int = 2

    fun onFailure(message: String): MyGalleryUiModel {
      return MyGalleryUiModel(FAILURE, message = message)
    }

    fun onLoaded(pages: List<Page>): MyGalleryUiModel {
      return MyGalleryUiModel(LOADED, pages = pages)
    }
  }

  @IntDef(BaseUiModel.INVALID, LOADED, FAILURE)
  internal annotation class Events
}