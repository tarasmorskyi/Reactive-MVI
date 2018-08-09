package com.tarasmorskyi.demoappkotlin.ui.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.widget.Toast
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.ActivityMainBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.ui.base.BaseActivity
import com.tarasmorskyi.demoappkotlin.ui.base.BaseUiModel
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryFragment
import com.tarasmorskyi.demoappkotlin.ui.main.my_gallery.MyGalleryFragment
import com.tarasmorskyi.demoappkotlin.ui.main.settings.SettingsFragment
import timber.log.Timber
import javax.inject.Inject




@ActivityScope
class MainActivity : BaseActivity<MainUiModel, MainEvent>(), MainView, OnTabSelectedListener {

  @Inject
  lateinit internal var presenter: MainPresenter
  private var binding: ActivityMainBinding? = null
  internal var selectedView: Int = -1
  private val GALLERY: Int = 0
  private val MY_GALLERY: Int = 1
  private val SETTINGS: Int = 2
  lateinit private var currentFragment: Fragment
  private var doubleBackToExitPressedOnce: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //analytics init
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    presenter!!.attach(this).compose<MainUiModel>({ this.setDefaults(it) }).subscribe(this)


    val item1 = AHBottomNavigationItem(R.string.gallery, R.drawable.abc_ic_star_black_16dp,
        R.color.black)
    val item2 = AHBottomNavigationItem(R.string.my_gallery, R.drawable.abc_ic_star_black_16dp,
        R.color.black)
    val item3 = AHBottomNavigationItem(R.string.settings, R.drawable.abc_ic_star_black_16dp,
        R.color.black)
    binding!!.bottomNavigation.addItem(item1)
    binding!!.bottomNavigation.addItem(item2)
    binding!!.bottomNavigation.addItem(item3)
    binding!!.bottomNavigation.setOnTabSelectedListener(this)
    injectView(GALLERY)
  }

  override fun onResume() {
    super.onResume()
    sendEvent(MainEvent.onLoaded())
  }

  override fun sendEvent(event: MainEvent) {
    presenter!!.event(event)
  }

  override fun onNext(uiModel: MainUiModel) {
    render(uiModel)
  }

  override fun render(uiModel: MainUiModel) {
    hideProgress()
    when (uiModel.model) {
      MainUiModel.FAILURE -> showWarningMessage(uiModel.message)
      MainUiModel.LOADING -> showProgress(getString(R.string.loading))
      BaseUiModel.INVALID -> Timber.w("render: unhandled [uiModel %s]", uiModel)
      else -> Timber.w("render: unhandled [uiModel %s]", uiModel)
    }
    super.render(uiModel)
  }

  private fun showWarningMessage(message: CharSequence) {
    Timber.d("showWarningMessage() called  with: messageText = [%s]", message)
    Snackbar.make(binding!!.root, message, Snackbar.LENGTH_LONG).show()
  }


  override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
    injectView(position)
    return true
  }


  private fun injectView(position: Int) {
    if (selectedView == position) {
      return
    }
    selectedView = position
    when (position) {
      GALLERY -> {
        injectFragment(R.id.container, GalleryFragment.Companion.newInstance(), getString(R.string.gallery))
        binding!!.bottomNavigation.currentItem = 0
      }
      MY_GALLERY -> {
        injectFragment(R.id.container, MyGalleryFragment.Companion.newInstance(), getString(R.string.my_gallery))

        binding!!.bottomNavigation.currentItem = 1
      }
      SETTINGS -> {
        injectFragment(R.id.container, SettingsFragment.Companion.newInstance(), getString(R.string.settings))

        binding!!.bottomNavigation.currentItem = 2
      }
    }
  }

  private fun getPreviousView(): Int {
    val fm = supportFragmentManager
    val count = fm.backStackEntryCount
    return if (count > 0) Integer.valueOf(fm.getBackStackEntryAt(count).name) else -1
  }

  private fun injectFragment(containerId: Int, fragment: Fragment, title: String) {
    this.currentFragment = fragment
//    binding.appBar.title.setText(title)
    val ts = supportFragmentManager.beginTransaction()
    val newView = selectedView.toString()
    if (newView != getPreviousView().toString()) {
      ts.replace(containerId, fragment)
      ts.commit()
    }
  }

  override fun onBackPressed() {
    if (selectedView == GALLERY) {
      if (doubleBackToExitPressedOnce) {
        super.onBackPressed()
        return
      }

      this.doubleBackToExitPressedOnce = true
      Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show()

      Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    } else {
      injectView(GALLERY)
    }
  }


  companion object {

    fun createIntent(context: Context): Intent {
      val intent = Intent(context, MainActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      return intent
    }
  }
}