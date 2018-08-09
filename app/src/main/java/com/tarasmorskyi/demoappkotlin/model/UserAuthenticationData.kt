package com.tarasmorskyi.demoappkotlin.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable


@PaperParcel
data class UserAuthenticationData(
    val accessToken: String = "",
    val expiresIn: Long = 0,
    val tokenType: String = "",
    val refreshToken: String = "",
    val accountUsername: String = "",
    val accountId: Long = 0
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelUserAuthenticationData.CREATOR
  }
}