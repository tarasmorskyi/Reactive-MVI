package com.tarasmorskyi.demoappkotlin.ui.main

import android.support.annotation.IntDef
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import paperparcel.PaperParcel
import java.util.Collections


@PaperParcel
data class MainUiModel(var model: Int = BaseUiModel.INVALID,
    var pages: List<Page> = Collections.emptyList(), var page: Page = Page(),
    var message: String = "") : BaseUiModel {

  companion object {
    @JvmField
    val CREATOR = PaperParcelMainUiModel.CREATOR

    const val FAILURE: Int = 1
    const val LOADED: Int = 2
    const val LOADING: Int = 3
    const val OPEN_DETAILS: Int = 4

    fun onLoading(): MainUiModel {
      return MainUiModel(LOADING)
    }

    fun onFailure(message: String): MainUiModel {
      return MainUiModel(LOADED, message = message)
    }

    fun onLoaded(pages: List<Page>): MainUiModel {
      return MainUiModel(LOADED, pages)
    }

    fun onOpenDetails(page: Page): MainUiModel {
      return MainUiModel(OPEN_DETAILS, page = page)
    }
  }

  @IntDef(BaseUiModel.INVALID, LOADED, FAILURE, LOADING, OPEN_DETAILS)
  internal annotation class Events
}