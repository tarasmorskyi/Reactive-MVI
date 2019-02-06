package com.tarasmorskyi.demoappkotlin.di

import com.tarasmorskyi.demoappkotlin.App
import com.tarasmorskyi.demoappkotlin.domain.DomainToolsModule
import com.tarasmorskyi.demoappkotlin.domain.repositories.RepositoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
      DomainToolsModule::class,
      RepositoryModule::class,
      AndroidSupportInjectionModule::class,
      SessionModule::class
    ])
interface AppComponent : AndroidInjector<App> {

  fun androidInjector(): AndroidInjector<App>

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>() {
    abstract override fun build(): AppComponent
  }
}