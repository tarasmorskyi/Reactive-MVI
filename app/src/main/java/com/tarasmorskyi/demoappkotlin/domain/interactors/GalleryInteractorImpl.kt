package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.domain.repositories.LocalRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.RemoteRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.Repositories
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class GalleryInteractorImpl @Inject constructor(repositories: Repositories) : GalleryInteractor {

  private val local: LocalRepository = repositories.local
  private val remote: RemoteRepository = repositories.remote

  override val posts: Maybe<List<Page>> = local.searchSettings
      .flatMap { searchSettings: SearchSettings ->
        local.userAuthenticationData.flatMap { remote.getPages(it.accessToken, searchSettings) }
      }

  override fun logout(): Completable {
    return Completable.complete()
  }

  override fun like(page: Page): Completable {
    return local.userAuthenticationData.flatMapCompletable { remote.likePost(it.accessToken, page) }
  }
}