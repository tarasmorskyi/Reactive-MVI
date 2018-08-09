package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.domain.repositories.LocalRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.RemoteRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.Repositories
import com.tarasmorskyi.demoappkotlin.utils.Constants.EMPTY_STRING
import io.reactivex.Maybe
import javax.inject.Inject


class SplashInteractorImpl @Inject internal constructor(
    repositories: Repositories) : SplashInteractor {
  override fun isLoggedIn(): Maybe<Boolean> {
    return local.userAuthenticationData.map { it.accessToken != EMPTY_STRING }
  }

  private val local: LocalRepository
  private val remote: RemoteRepository

  init {
    local = repositories.local
    remote = repositories.remote
  }
}