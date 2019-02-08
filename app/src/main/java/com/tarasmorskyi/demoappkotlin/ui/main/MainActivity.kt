package com.tarasmorskyi.demoappkotlin.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.widget.Toast
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.ActivityMainBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.ui.base.BaseActivity
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryFragment
import com.tarasmorskyi.demoappkotlin.ui.main.my_gallery.MyGalleryFragment
import com.tarasmorskyi.demoappkotlin.ui.main.settings.SettingsFragment
import javax.inject.Inject


@ActivityScope
class MainActivity : BaseActivity<MainUiModel, MainEvent>(), MainView, OnTabSelectedListener {

  @Inject
  internal lateinit var eventManager: MainEventManager
  private lateinit var binding: ActivityMainBinding
  private var selectedView: Int = -1
  private lateinit var currentFragment: Fragment
  private var doubleBackToExitPressedOnce: Boolean = false

  @SuppressLint("PrivateResource")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //analytics init
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    eventManager.attach().compose<MainUiModel> { this.setDefaults(it) }.subscribe(this)


    val item1 = AHBottomNavigationItem(R.string.gallery, R.drawable.abc_ic_star_black_16dp,
        R.color.black)
    val item2 = AHBottomNavigationItem(R.string.my_gallery, R.drawable.abc_ic_star_black_16dp,
        R.color.black)
    val item3 = AHBottomNavigationItem(R.string.settings, R.drawable.abc_ic_star_black_16dp,
        R.color.black)
    binding.bottomNavigation.addItem(item1)
    binding.bottomNavigation.addItem(item2)
    binding.bottomNavigation.addItem(item3)
    binding.bottomNavigation.setOnTabSelectedListener(this)
    injectView(GALLERY)
  }

  override fun sendEvent(event: MainEvent) {
    eventManager.event(event)
  }

  override fun onNext(uiModel: MainUiModel) {
    render(uiModel)
  }

  override fun render(uiModel: MainUiModel) {
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
        injectFragment(R.id.container, GalleryFragment.newInstance(),
            getString(R.string.gallery))
        binding.bottomNavigation.currentItem = 0
      }
      MY_GALLERY -> {
        injectFragment(R.id.container, MyGalleryFragment.newInstance(),
            getString(R.string.my_gallery))

        binding.bottomNavigation.currentItem = 1
      }
      SETTINGS -> {
        injectFragment(R.id.container, SettingsFragment.newInstance(),
            getString(R.string.settings))

        binding.bottomNavigation.currentItem = 2
      }
    }
  }

  private fun getPreviousView(): Int {
    val fm = supportFragmentManager
    val count = fm.backStackEntryCount
    return if (count > 0) Integer.valueOf(
        fm.getBackStackEntryAt(count).name?.let { it } ?: "-1") else -1
  }

  private fun injectFragment(containerId: Int, fragment: Fragment, title: String) {
    this.currentFragment = fragment
    val ts = supportFragmentManager.beginTransaction()
    val newView = selectedView.toString()
    if (newView != getPreviousView().toString()) {
      ts.replace(containerId, fragment, title)
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

    private const val GALLERY: Int = 0
    private const val MY_GALLERY: Int = 1
    private const val SETTINGS: Int = 2

    fun createIntent(context: Context): Intent {
      val intent = Intent(context, MainActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      return intent
    }
  }
}