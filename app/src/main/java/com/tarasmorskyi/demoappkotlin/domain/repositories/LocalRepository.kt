package com.tarasmorskyi.demoappkotlin.domain.repositories

import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import io.reactivex.Completable
import io.reactivex.Maybe


interface LocalRepository {

  val userAuthenticationData: Maybe<UserAuthenticationData>

  fun setUserAuthenticationData(userAuthenticationData: UserAuthenticationData): Completable

  val searchSettings: Maybe<SearchSettings>

  fun setSearchSettings(searchSettings: SearchSettings): Completable
}
