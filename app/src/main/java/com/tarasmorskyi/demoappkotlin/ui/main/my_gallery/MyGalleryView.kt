package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import com.tarasmorskyi.demoappkotlin.ui.base.BaseView
import io.reactivex.Observer


internal interface MyGalleryView : BaseView<MyGalleryUiModel>, Observer<MyGalleryUiModel>