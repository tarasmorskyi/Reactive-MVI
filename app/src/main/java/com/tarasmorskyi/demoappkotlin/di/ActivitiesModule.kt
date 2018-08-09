package com.tarasmorskyi.demoappkotlin.di

import com.tarasmorskyi.demoappkotlin.ui.login.LoginActivity
import com.tarasmorskyi.demoappkotlin.ui.login.LoginActivityModule
import com.tarasmorskyi.demoappkotlin.ui.main.MainActivity
import com.tarasmorskyi.demoappkotlin.ui.main.MainActivityModule
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashActivity
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivitiesModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
  internal abstract fun mainActivity(): MainActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = arrayOf(SplashActivityModule::class))
  internal abstract fun splashActivity(): SplashActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
  internal abstract fun loginActivity(): LoginActivity

}