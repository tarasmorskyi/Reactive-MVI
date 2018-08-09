package com.tarasmorskyi.demoappkotlin.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

class LifecycleHandler : Application.ActivityLifecycleCallbacks {
  private var started: Int
  private var stopped: Int
  private val events = BehaviorRelay.create<Events>()

  val isApplicationVisible: Boolean
    get() = started > stopped

  init {
    started = 0
    stopped = 0
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
    events.accept(Events.CREATED)
  }

  override fun onActivityDestroyed(activity: Activity) {
    events.accept(Events.CREATED)
  }

  override fun onActivityResumed(activity: Activity) {
    events.accept(Events.RESUMED)
  }

  override fun onActivityPaused(activity: Activity) {
    events.accept(Events.PAUSED)
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    //not used
  }

  override fun onActivityStarted(activity: Activity) {
    ++started
    events.accept(Events.STARTED)
  }

  override fun onActivityStopped(activity: Activity) {
    ++stopped
    events.accept(Events.STOPPED)
  }

  fun getEvents(): Observable<Events> {
    return events
  }

}