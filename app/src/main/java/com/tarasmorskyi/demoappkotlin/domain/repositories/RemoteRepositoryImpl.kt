package com.tarasmorskyi.demoappkotlin.domain.repositories

import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.model.Verse
import dagger.Reusable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


@Reusable
class RemoteRepositoryImpl @Inject internal constructor(retrofit: Retrofit) : RemoteRepository {

  private val service: RemoteRepository.RemoteService

  init {
    this.service = retrofit.create(RemoteRepository.RemoteService::class.java)
  }

  override fun getPages(token: String, searchSettings: SearchSettings): Maybe<List<Page>> {
    return service.getPages("Bearer " + token, searchSettings.section,
        searchSettings.sort, searchSettings.window, 0, searchSettings.showViral,
        searchSettings.mature)
        .subscribeOn(Schedulers.io())
        .compose(RxUtils.transformMaybeResult())
  }

  override fun getMyPages(token: String, username: String): Maybe<List<Page>> {
    return service.getMyPages("Bearer " + token, username)
        .subscribeOn(Schedulers.io())
        .compose(RxUtils.transformMaybeResult())
  }

  override fun getVerses(accessToken: String, chapterNumber: Int): Maybe<List<Verse>> {
    return service.getVerses("Bearer $accessToken", chapterNumber)
        .subscribeOn(Schedulers.io())
        .compose(RxUtils.transformMaybeResult())
  }

  override fun likePost(token: String, page: Page): Completable {
    var link = if(page.images.size > 0) page.images[0].id else page.id
    return service.votePost("Bearer $token", link)
        .subscribeOn(Schedulers.io())
        .toSingle().toCompletable()
  }
}