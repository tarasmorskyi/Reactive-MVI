package com.tarasmorskyi.demoappkotlin.domain.repositories

import com.serjltt.moshi.adapters.Wrapped
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.model.Verse
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface RemoteRepository {

  fun getPages(token: String, searchSettings: SearchSettings): Maybe<List<Page>>

  fun getMyPages(token: String, username: String): Maybe<List<Page>>

  fun likePost(token: String, page: Page): Completable

  fun getVerses(accessToken: String, chapterNumber: Int): Maybe<List<Verse>>

  interface RemoteService {

    @Wrapped(path = arrayOf("data"))
    @GET("3/gallery/{section}/{sort}/{window}/{page}")
    fun getPages(
        @Header("Authorization") clientId: String, @Path("section") section: String,
        @Path("sort") sort: String, @Path("window") window: String,
        @Path("page") page: Int, @Query("showViral") showViral: Boolean,
        @Query("mature") mature: Boolean)
        : Maybe<Result<List<Page>>>

    @Wrapped(path = arrayOf("data"))
    @GET("3/account/{username}/favorites/0/newest")
    fun getMyPages(
        @Header("Authorization") clientId: String, @Path("username") username: String)
        : Maybe<Result<List<Page>>>

    @POST("3/image/{imageHash}/favorite")
    fun votePost(
        @Header("Authorization") clientId: String, @Path("imageHash") imageHash: String)
        : Maybe<Result<String>>

    @GET("api/v1/chapters/{num}/verses")
    fun getVerses(
        @Header("Authorization") accessToken: String, @Path(
            "num") chapterNumber: Int): Maybe<Result<List<Verse>>>
  }

}
