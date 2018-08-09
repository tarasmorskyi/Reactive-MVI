package com.tarasmorskyi.demoappkotlin.domain.repositories

import com.tarasmorskyi.demoappkotlin.domain.storage.Storage
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject


class LocalRepositoryImpl @Inject internal constructor(
    private val storage: Storage) : LocalRepository {

  override val userAuthenticationData: Maybe<UserAuthenticationData>
    get() = Maybe.fromCallable { storage.get(USER, UserAuthenticationData::class.java,
        UserAuthenticationData()) }

  override fun setUserAuthenticationData(userAuthenticationData: UserAuthenticationData): Completable {
    return Completable.fromAction { storage.set(USER, userAuthenticationData, UserAuthenticationData::class.java) }
  }

  override val searchSettings: Maybe<SearchSettings>
    get() = Maybe.fromCallable { storage.get(SEARCH_SETTINGS, SearchSettings::class.java,
        SearchSettings()) }

  override fun setSearchSettings(searchSettings: SearchSettings): Completable {
    return Completable.fromAction { storage.set(SEARCH_SETTINGS, searchSettings, SearchSettings::class.java) }
  }

  companion object {
    private val USER = "user"
    private val SEARCH_SETTINGS = "search_settings"
  }
}
