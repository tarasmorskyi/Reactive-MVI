package com.tarasmorskyi.demoappkotlin.ui.main

import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryFragment
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.LikeDialog
import com.tarasmorskyi.demoappkotlin.ui.main.my_gallery.MyGalleryFragment
import com.tarasmorskyi.demoappkotlin.ui.main.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

  @ContributesAndroidInjector
  internal abstract fun galleryFragment(): GalleryFragment

  @ContributesAndroidInjector
  internal abstract fun myGalleryFragment(): MyGalleryFragment

  @ContributesAndroidInjector
  internal abstract fun settingsFragment(): SettingsFragment

  @ContributesAndroidInjector
  internal abstract fun likeDialog(): LikeDialog
}