package com.tarasmorskyi.demoappkotlin.domain.interactors

import dagger.Binds
import dagger.Module


@Module
abstract class DomainInteractorsModule {

  @Binds
  internal abstract fun provideMainInteractor(mainInteractor: MainInteractorImpl): MainInteractor

  @Binds
  internal abstract fun provideSplashInteractor(
      splashInteractor: SplashInteractorImpl): SplashInteractor

  @Binds
  internal abstract fun provideLoginInteractor(
      loginInteractor: LoginInteractorImpl): LoginInteractor

  @Binds
  internal abstract fun provideGalleryInteractor(
      galleryInteractor: GalleryInteractorImpl): GalleryInteractor

  @Binds
  internal abstract fun provideMyGalleryInteractor(
      myGalleryInteractor: MyGalleryInteractorImpl): MyGalleryInteractor

  @Binds
  internal abstract fun provideSettingsInteractor(
      settingsInteractor: SettingsInteractorImpl): SettingsInteractor
}