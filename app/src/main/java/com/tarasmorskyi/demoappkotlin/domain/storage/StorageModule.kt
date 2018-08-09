package com.tarasmorskyi.demoappkotlin.domain.storage

import com.squareup.moshi.Moshi
import com.tarasmorskyi.demoappkotlin.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule {

  @Provides
  @Singleton
  internal fun provideStorage(application: App, moshi: Moshi): Storage {
    return Storage.getDefault(application, moshi)
  }
}