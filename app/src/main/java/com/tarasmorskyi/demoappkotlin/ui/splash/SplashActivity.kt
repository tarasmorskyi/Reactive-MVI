package com.tarasmorskyi.demoappkotlin.ui.splash

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.ActivitySplashBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.ui.base.BaseActivity
import com.tarasmorskyi.demoappkotlin.ui.login.LoginActivity
import com.tarasmorskyi.demoappkotlin.ui.main.MainActivity
import javax.inject.Inject

@ActivityScope
class SplashActivity : BaseActivity<SplashUiModel, SplashEvent>(), SplashView {

  @Inject
  internal lateinit var eventManager: SplashEventManager
  private lateinit var binding: ActivitySplashBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //analytics init
    binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    eventManager.attach(this).compose<SplashUiModel> { this.setDefaults(it) }.subscribe(this)
  }

  override fun onResume() {
    super.onResume()
    sendEvent(SplashEvent.Loaded)
  }

  override fun sendEvent(event: SplashEvent) {
    eventManager.event(event)
  }

  override fun onNext(uiModel: SplashUiModel) {
    render(uiModel)
  }

  override fun render(uiModel: SplashUiModel) {
    hideProgress()
    when (uiModel) {
      SplashUiModel.GoToLogin -> startActivity(LoginActivity.createIntent(this))
      SplashUiModel.GoToMain -> startActivity(MainActivity.createIntent(this))
    }
  }

  companion object {

    fun createIntent(context: Context): Intent {
      val intent = Intent(context, SplashActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      return intent
    }
  }
}