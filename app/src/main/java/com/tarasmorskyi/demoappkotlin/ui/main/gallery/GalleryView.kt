package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import com.tarasmorskyi.demoappkotlin.ui.base.BaseView
import io.reactivex.Observer


internal interface GalleryView : BaseView<GalleryUiModel>, Observer<GalleryUiModel>