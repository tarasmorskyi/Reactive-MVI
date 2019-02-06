package com.tarasmorskyi.demoappkotlin.model

import com.tarasmorskyi.demoappkotlin.utils.Constants.EMPTY_STRING
import paperparcel.PaperParcel
import paperparcel.PaperParcelable


@PaperParcel
data class UserAuthenticationData(
    val accessToken: String = EMPTY_STRING,
    val expiresIn: Long = 0,
    val tokenType: String = EMPTY_STRING,
    val refreshToken: String = EMPTY_STRING,
    val accountUsername: String = EMPTY_STRING,
    val accountId: Long = 0
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelUserAuthenticationData.CREATOR
  }
}