package com.tarasmorskyi.demoappkotlin.domain.repositories

import dagger.Lazy
import dagger.Reusable
import javax.inject.Inject


@Reusable
class Repositories @Inject constructor(
    private val remoteRepository: Lazy<RemoteRepository>,
    private val localRepository: Lazy<LocalRepository>) {

  val remote: RemoteRepository
    get() = remoteRepository.get()

  val local: LocalRepository
    get() = localRepository.get()
}