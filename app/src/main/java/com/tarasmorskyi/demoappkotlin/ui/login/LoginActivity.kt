package com.tarasmorskyi.demoappkotlin.ui.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.ActivityLoginBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import com.tarasmorskyi.demoappkotlin.ui.base.BaseActivity
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import com.tarasmorskyi.demoappkotlin.ui.login.LoginUiModel.Companion.FAILURE
import com.tarasmorskyi.demoappkotlin.ui.login.LoginUiModel.Companion.GO_TO_SPLASH
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashActivity
import timber.log.Timber
import javax.inject.Inject



@ActivityScope
class LoginActivity : BaseActivity<LoginUiModel, LoginEvent>(), LoginView {

  @Inject
  lateinit internal var presenter: LoginPresenter
  private var binding: ActivityLoginBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //analytics init
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    presenter!!.attach(this).compose<LoginUiModel>({ this.setDefaults(it) }).subscribe(this)
    var webClient: WebViewClient = object : WebViewClient() {
      override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        parseLoginDataIfLoggedIn(url)
      }
    }
    binding!!.webView.setWebViewClient(webClient)
    binding!!.webView.settings.userAgentString = "demoapp"
    binding!!.webView.clearFormData()

    binding!!.webView.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=9a9f8a8c12cb9ce&response_type=token&state=demoapp")
  }

  private fun parseLoginDataIfLoggedIn(url: String?) {
    if (url!!.contains("imgur.com") && url!!.contains("access_token")) {
      val params = url!!.substring(url!!.indexOf("#") + 1).split("&")
      val map = HashMap<String, String>()
      for (param in params) {
        val name = param.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
        val value = param.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1]
        map[name] = value
      }
      var userAuthenticationData = UserAuthenticationData(map.get("access_token")!!,
          map.get("expires_in")?.toLong()!!, map.get("token_type")!!, map.get("refresh_token")!!,
          map.get("account_username")!!, map.get("account_id")?.toLong()!!)
      sendEvent(LoginEvent.onLogin(userAuthenticationData))
    }
  }

  override fun onResume() {
    super.onResume()
    sendEvent(LoginEvent.onLoaded())
  }

  override fun sendEvent(event: LoginEvent) {
    presenter!!.event(event)
  }

  override fun onNext(uiModel: LoginUiModel) {
    render(uiModel)
  }

  override fun render(uiModel: LoginUiModel) {
    hideProgress()
    when (uiModel.model) {
      FAILURE -> showWarningMessage(uiModel.message)
      GO_TO_SPLASH -> startActivity(SplashActivity.createIntent(this))
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
      val intent = Intent(context, LoginActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      return intent
    }
  }
}
