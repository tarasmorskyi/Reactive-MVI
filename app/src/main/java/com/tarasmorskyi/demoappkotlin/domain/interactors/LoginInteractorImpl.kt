package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.domain.repositories.LocalRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.RemoteRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.Repositories
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import io.reactivex.Completable
import javax.inject.Inject


class LoginInteractorImpl @Inject constructor(repositories: Repositories) : LoginInteractor {

  private val local: LocalRepository
  private val remote: RemoteRepository

  init {
    local = repositories.local
    remote = repositories.remote
  }

  override fun login(userAuthenticationData: UserAuthenticationData): Completable {
    return local.setUserAuthenticationData(userAuthenticationData)
  }
}