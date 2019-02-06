package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.domain.repositories.LocalRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.RemoteRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.Repositories
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(repositories: Repositories) : SettingsInteractor {

  private val local: LocalRepository = repositories.local
  private val remote: RemoteRepository = repositories.remote

  override val settings: Maybe<SearchSettings> = local.searchSettings

  override fun setSettings(searchSettings: SearchSettings): Completable {
    return local.setSearchSettings(searchSettings)
  }

  override fun logout(): Completable {
    return local.setUserAuthenticationData(
        UserAuthenticationData())
  }
}