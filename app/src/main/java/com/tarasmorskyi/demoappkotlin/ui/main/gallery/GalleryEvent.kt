package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent

sealed class GalleryEvent : BaseEvent {

  object Loaded : GalleryEvent()

  data class ListClicked(val page: Page) : GalleryEvent()

  data class Like(val page: Page) : GalleryEvent()
}