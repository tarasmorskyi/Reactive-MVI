package com.tarasmorskyi.demoappkotlin.domain

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.tarasmorskyi.demoappkotlin.BuildConfig
import com.tarasmorskyi.demoappkotlin.domain.storage.StorageModule
import com.tarasmorskyi.demoappkotlin.utils.MoshiDateAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [StorageModule::class])
class DomainToolsModule {

  private val TIMEOUT = 60

  internal val okHttpClient: OkHttpClient
    @Provides @Singleton get() {
      val builder = OkHttpClient.Builder()
      if (BuildConfig.DEBUG) {
        builder.addNetworkInterceptor(StethoInterceptor())
      }
      return builder.build()
    }

  @Provides
  @Singleton
  internal fun provideRestAdapter(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    builder.baseUrl(BuildConfig.SERVER)
    return builder.build()
  }

  @Provides
  @Singleton
  internal fun provideDefaultMoshiBuilder(): Moshi.Builder {
    return Moshi.Builder()
        .add(MoshiDateAdapter())
        .add(Wrapped.ADAPTER_FACTORY)
  }

  @Provides
  @Singleton
  internal fun provideMoshi(moshiBuilder: Moshi.Builder): Moshi {
    return moshiBuilder.build()
  }
}