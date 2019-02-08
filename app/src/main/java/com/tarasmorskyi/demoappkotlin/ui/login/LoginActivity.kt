package com.tarasmorskyi.demoappkotlin.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.ActivityLoginBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.model.UserAuthenticationData
import com.tarasmorskyi.demoappkotlin.ui.base.BaseActivity
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashActivity
import java.util.HashMap
import javax.inject.Inject



@ActivityScope
class LoginActivity : BaseActivity<LoginUiModel, LoginEvent>(), LoginView {

  @Inject
  internal lateinit var eventManager: LoginEventManager
  private lateinit var binding: ActivityLoginBinding

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //analytics init
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    eventManager.attach(this).compose<LoginUiModel> { this.setDefaults(it) }.subscribe(this)
    val webClient: WebViewClient = object : WebViewClient() {
      override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        parseLoginDataIfLoggedIn(url)
      }
    }
    binding.webView.webViewClient = webClient
    binding.webView.settings.userAgentString = "demoapp"
    binding.webView.clearFormData()
    binding.webView.settings.javaScriptEnabled = true

    binding.webView.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=9a9f8a8c12cb9ce&response_type=token&state=demoapp")
  }

  private fun parseLoginDataIfLoggedIn(url: String?) {
    if (url!!.contains("imgur.com") && url.contains("access_token")) {
      val params = url.substring(url.indexOf("#") + 1).split("&")
      val map = HashMap<String, String>()
      for (param in params) {
        val name = param.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        val value = param.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        map[name] = value
      }
      val userAuthenticationData = UserAuthenticationData(map["access_token"]!!,
          map["expires_in"]?.toLong()!!, map["token_type"]!!, map["refresh_token"]!!,
          map["account_username"]!!, map["account_id"]?.toLong()!!)
      sendEvent(LoginEvent.Login(userAuthenticationData))
    }
  }

  override fun onResume() {
    super.onResume()
    sendEvent(LoginEvent.Loaded)
  }

  override fun sendEvent(event: LoginEvent) {
    eventManager.event(event)
  }

  override fun onNext(uiModel: LoginUiModel) {
    render(uiModel)
  }

  override fun render(uiModel: LoginUiModel) {
    hideProgress()
    when (uiModel) {
      is LoginUiModel.GoToSplash -> startActivity(SplashActivity.createIntent(this))
    }
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
