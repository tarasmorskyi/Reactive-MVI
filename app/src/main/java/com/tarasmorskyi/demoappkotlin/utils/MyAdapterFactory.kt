package com.tarasmorskyi.demoappkotlin.utils

import com.squareup.moshi.JsonAdapter


//@MoshiAdapterFactory(nullSafe = true)
abstract class MyAdapterFactory : JsonAdapter.Factory {
  companion object {

    fun create(): JsonAdapter.Factory {
      return JsonAdapter.Factory { type, annotations, moshi -> null }
    }
  }
}