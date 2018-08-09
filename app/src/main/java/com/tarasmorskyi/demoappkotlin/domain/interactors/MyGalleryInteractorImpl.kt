package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.domain.repositories.LocalRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.RemoteRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.Repositories
import com.tarasmorskyi.demoappkotlin.model.Page
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class MyGalleryInteractorImpl @Inject constructor(repositories: Repositories) : MyGalleryInteractor {

  private val local: LocalRepository
  private val remote: RemoteRepository

  override val posts: Maybe<List<Page>>
    get() = local.userAuthenticationData.flatMap { remote.getMyPages(it.accessToken, it.accountUsername) }


  init {
    local = repositories.local
    remote = repositories.remote
  }

  override fun logout(): Completable {
    return Completable.complete()
  }
}