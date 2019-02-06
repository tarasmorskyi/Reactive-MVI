package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel


sealed class GalleryUiModel : BaseUiModel {

  data class Loaded(val pages: List<Page>) : GalleryUiModel()

  data class ShowLikeDialog(val page: Page) : GalleryUiModel()
}