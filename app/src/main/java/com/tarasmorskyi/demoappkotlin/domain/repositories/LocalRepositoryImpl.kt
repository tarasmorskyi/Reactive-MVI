package com.tarasmorskyi.demoappkotlin.domain.repositories

import com.tarasmorskyi.demoappkotlin.domain.storage.Storage
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject


class LocalRepositoryImpl @Inject internal constructor(
    private val storage: Storage) : LocalRepository {

  override val userAuthenticationData: Maybe<UserAuthenticationData> = Maybe.fromCallable {
    storage[USER, UserAuthenticationData::class.java, UserAuthenticationData()]
  }

  override fun setUserAuthenticationData(
      userAuthenticationData: UserAuthenticationData): Completable {
    return Completable.fromAction {
      storage[USER, userAuthenticationData] = UserAuthenticationData::class.java
    }
  }

  override val searchSettings: Maybe<SearchSettings> = Maybe.fromCallable {
      storage[SEARCH_SETTINGS, SearchSettings::class.java, SearchSettings()]
    }

  override fun setSearchSettings(searchSettings: SearchSettings): Completable {
    return Completable.fromAction {
      storage[SEARCH_SETTINGS, searchSettings] = SearchSettings::class.java
    }
  }

  companion object {
    private val USER = "user"
    private val SEARCH_SETTINGS = "search_settings"
  }
}
