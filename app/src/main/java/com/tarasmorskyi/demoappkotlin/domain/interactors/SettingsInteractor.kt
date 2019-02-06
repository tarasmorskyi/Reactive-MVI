package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import io.reactivex.Completable
import io.reactivex.Maybe

interface SettingsInteractor {

  val settings: Maybe<SearchSettings>

  fun setSettings(searchSettings: SearchSettings): Completable

  fun logout(): Completable
}