package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import io.reactivex.Completable

interface LoginInteractor {
  fun login(userAuthenticationData: UserAuthenticationData): Completable
}