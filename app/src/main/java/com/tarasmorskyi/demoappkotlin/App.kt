package com.tarasmorskyi.demoappkotlin

import android.content.Context
import android.support.multidex.MultiDex
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tarasmorskyi.demoappkotlin.di.AppComponent
import com.tarasmorskyi.demoappkotlin.di.DaggerAppComponent
import com.tarasmorskyi.demoappkotlin.utils.LifecycleHandler
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class App : DaggerApplication() {

  lateinit var applicationEnvironment: ApplicationEnvironment
  lateinit var lifecycleHandler: LifecycleHandler

  companion object {
    private lateinit var application: App

    fun getInstance() : App {
      return application
    }
  }

  init {
    application = this
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
    applicationEnvironment = ApplicationEnvironment(application)
    applicationEnvironment.init()
    lifecycleHandler = LifecycleHandler()
    registerActivityLifecycleCallbacks(lifecycleHandler)
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val builder : AppComponent.Builder = DaggerAppComponent.builder()
    builder.seedInstance(this)
    return builder.build().androidInjector()
  }
}