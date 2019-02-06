package com.tarasmorskyi.demoappkotlin.domain.interactors

import com.tarasmorskyi.demoappkotlin.domain.repositories.LocalRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.RemoteRepository
import com.tarasmorskyi.demoappkotlin.domain.repositories.Repositories
import javax.inject.Inject


class MainInteractorImpl @Inject constructor(repositories: Repositories) : MainInteractor {

  private val local: LocalRepository = repositories.local
  private val remote: RemoteRepository = repositories.remote

}
