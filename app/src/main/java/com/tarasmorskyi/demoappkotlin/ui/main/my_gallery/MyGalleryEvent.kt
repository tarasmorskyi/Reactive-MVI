package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import com.tarasmorskyi.demoappkotlin.ui.base.BaseEvent

sealed class MyGalleryEvent : BaseEvent {

  object Loaded : MyGalleryEvent()
}