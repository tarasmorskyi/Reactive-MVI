package com.tarasmorskyi.demoappkotlin.domain.repositories

import com.tarasmorskyi.demoappkotlin.domain.DomainToolsModule
import dagger.Binds
import dagger.Module


@Module(includes = [DomainToolsModule::class])
abstract class RepositoryModule {

  @Binds
  protected abstract fun localRepository(localRepository: LocalRepositoryImpl): LocalRepository

  @Binds
  protected abstract fun remoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository
}
