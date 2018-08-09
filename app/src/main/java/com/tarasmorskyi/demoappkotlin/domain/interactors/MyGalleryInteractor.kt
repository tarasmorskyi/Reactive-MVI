package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.model.Page
import io.reactivex.Completable
import io.reactivex.Maybe

interface MyGalleryInteractor {
  val posts: Maybe<List<Page>>
  fun logout(): Completable
}