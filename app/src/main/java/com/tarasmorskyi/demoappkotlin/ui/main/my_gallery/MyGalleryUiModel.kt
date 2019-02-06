package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel


sealed class MyGalleryUiModel : BaseUiModel {

  data class Loaded(val pages: List<Page>) : MyGalleryUiModel()
}