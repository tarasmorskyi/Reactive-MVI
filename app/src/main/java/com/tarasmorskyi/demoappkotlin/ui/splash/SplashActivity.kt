package com.tarasmorskyi.demoappkotlin.ui.splash

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.ActivitySplashBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.ui.base.BaseActivity
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import com.tarasmorskyi.demoappkotlin.ui.login.LoginActivity
import com.tarasmorskyi.demoappkotlin.ui.main.MainActivity
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashUiModel.Companion.FAILURE
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashUiModel.Companion.GO_TO_LOGIN
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashUiModel.Companion.GO_TO_MAIN
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class SplashActivity : BaseActivity<SplashUiModel, SplashEvent>(), SplashView {

  @Inject
  lateinit internal var presenter: SplashPresenter
  private var binding: ActivitySplashBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //analytics init
    binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    presenter!!.attach(this).compose<SplashUiModel>({ this.setDefaults(it) }).subscribe(this)
  }

  override fun onResume() {
    super.onResume()
    sendEvent(SplashEvent.onLoaded())
  }

  override fun sendEvent(event: SplashEvent) {
    presenter!!.event(event)
  }

  override fun onNext(uiModel: SplashUiModel) {
    render(uiModel)
  }

  override fun render(uiModel: SplashUiModel) {
    hideProgress()
    when (uiModel.model) {
      FAILURE -> showWarningMessage(uiModel.message)
      GO_TO_LOGIN -> startActivity(LoginActivity.createIntent(this))
      GO_TO_MAIN -> startActivity(MainActivity.createIntent(this))
      BaseUiModel.INVALID -> Timber.w("render: unhandled [uiModel %s]", uiModel)
      else -> Timber.w("render: unhandled [uiModel %s]", uiModel)
    }
    super.render(uiModel)
  }

  private fun showWarningMessage(message: CharSequence) {
    Timber.d("showWarningMessage() called  with: messageText = [%s]", message)
    Snackbar.make(binding!!.root, message, Snackbar.LENGTH_LONG).show()
  }

  override fun onDestroy() {
    super.onDestroy()
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