package com.tarasmorskyi.demoappkotlin.domain.interactors

import io.reactivex.Maybe


interface SplashInteractor {

  fun isLoggedIn(): Maybe<Boolean>
}