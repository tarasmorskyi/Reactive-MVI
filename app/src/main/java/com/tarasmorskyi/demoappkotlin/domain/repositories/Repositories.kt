package com.tarasmorskyi.demoappkotlin.domain.repositories

import dagger.Lazy
import dagger.Reusable
import javax.inject.Inject


@Reusable
class Repositories @Inject constructor(
    remoteRepository: Lazy<RemoteRepository>,
    localRepository: Lazy<LocalRepository>
) {

  val remote: RemoteRepository = remoteRepository.get()

  val local: LocalRepository = localRepository.get()
}