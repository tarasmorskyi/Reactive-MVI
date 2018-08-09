package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import paperparcel.PaperParcel
import java.util.Collections


@PaperParcel
data class GalleryUiModel(var model: Int = BaseUiModel.INVALID, var message: String = "",
    var pages: List<Page> = Collections.emptyList(),
    var page: Page = Page()) : BaseUiModel {

  companion object {
    @JvmField
    val CREATOR = PaperParcelGalleryUiModel.CREATOR

    const val FAILURE: Int = 1
    const val LOADED: Int = 2
    const val LIKE_DIALOG: Int = 3

    fun onFailure(message: String): GalleryUiModel {
      return GalleryUiModel(LOADED, message = message)
    }

    fun onLoaded(pages: List<Page>): GalleryUiModel {
      return GalleryUiModel(LOADED, pages = pages)
    }

    fun onShowLikeDialog(page: Page): GalleryUiModel {
      return GalleryUiModel(LIKE_DIALOG, page = page)
    }
  }

  @IntDef(BaseUiModel.INVALID, LOADED, FAILURE, LIKE_DIALOG)
  internal annotation class Events
}