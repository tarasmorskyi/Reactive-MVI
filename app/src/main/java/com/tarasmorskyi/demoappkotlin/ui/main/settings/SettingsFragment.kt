package com.tarasmorskyi.demoappkotlin.ui.main.settings

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.FragmentSettingsBinding
import com.tarasmorskyi.demoappkotlin.model.SearchSettings
import com.tarasmorskyi.demoappkotlin.ui.base.BaseFragment
import com.tarasmorskyi.demoappkotlin.ui.main.settings.SettingsUiModel.Companion.LOADED
import com.tarasmorskyi.demoappkotlin.ui.main.settings.SettingsUiModel.Companion.LOGOUT
import com.tarasmorskyi.demoappkotlin.ui.main.settings.ToggleButtonGroupTableLayout.RadioButtonChecked
import com.tarasmorskyi.demoappkotlin.ui.splash.SplashActivity
import timber.log.Timber
import javax.inject.Inject


class SettingsFragment: BaseFragment<SettingsEvent, SettingsUiModel>(), SettingsView, RadioButtonChecked {

  @Inject
  internal
  lateinit var presenter: SettingsPresenter

  lateinit var searchSettings: SearchSettings

  lateinit var binding: FragmentSettingsBinding

  companion object {
    fun newInstance(): SettingsFragment {
      return SettingsFragment()
    }
  }

  override protected fun sendEvent(event: SettingsEvent) {
    presenter!!.event(event)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
    return binding!!.getRoot()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    setDefaults(presenter.attach(this)).subscribe(this)
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    Timber.d("onViewStateRestored() called  with: savedInstanceState = [%s]", savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    presenter!!.event(SettingsEvent.onLoaded())
  }

  override fun onDetach() {
    super.onDetach()
    presenter!!.detach()
  }

  override fun render(uiModel: SettingsUiModel) {
    when (uiModel.model) {
      LOADED -> setupSettings(uiModel.searchSettings)
      LOGOUT -> startActivity(SplashActivity.createIntent(activity!!))
    }
  }

  private fun setupSettings(searchSettings: SearchSettings) {
    this.searchSettings = searchSettings
    when (searchSettings.section) {
      "hot" -> binding.rgSection.onClick(binding.hot)
      "top" -> binding.rgSection.onClick(binding.top)
      "user" -> binding.rgSection.onClick(binding.user)
    }
    when (searchSettings.sort) {
      "viral" -> binding.rgSort.onClick(binding.viral)
      "top" -> binding.rgSort.onClick(binding.topSort)
      "time" -> binding.rgSort.onClick(binding.time)
    }
    when (searchSettings.window) {
      "day" -> binding.rgWindow.onClick(binding.day)
      "week" -> binding.rgWindow.onClick(binding.week)
      "month" -> binding.rgWindow.onClick(binding.month)
      "year" -> binding.rgWindow.onClick(binding.year)
      "all" -> binding.rgWindow.onClick(binding.all)
    }
    binding.rgSection.onRadioButtonChecked(this)
    binding.rgSort.onRadioButtonChecked(this)
    binding.rgWindow.onRadioButtonChecked(this)

    binding.showViral.isChecked = searchSettings.showViral
    binding.mature.isChecked = searchSettings.mature
    binding.showViral.setOnCheckedChangeListener{ compoundButton, b ->
      run {
        searchSettings.showViral = b
        sendEvent(SettingsEvent.saveSettings(searchSettings))
      }
    }
    binding.mature.setOnCheckedChangeListener{ compoundButton, b ->
      run {
        searchSettings.mature = b
        sendEvent(SettingsEvent.saveSettings(searchSettings))
      }
    }
    binding.logout.setOnClickListener{view -> sendEvent(SettingsEvent.onLogout())}
  }

  override fun onNext(homeUiModel: SettingsUiModel) {
    render(homeUiModel)
  }

  override fun onRadioButtonChecked(view: View) {
    when (view.id) {
      binding.hot.id -> searchSettings.section = "hot"
      binding.top.id -> searchSettings.section = "top"
      binding.user.id -> searchSettings.section = "user"

      binding.viral.id -> searchSettings.sort = "viral"
      binding.topSort.id -> searchSettings.sort = "top"
      binding.time.id -> searchSettings.sort = "time"

      binding.day.id -> searchSettings.window = "day"
      binding.week.id -> searchSettings.window = "week"
      binding.month.id -> searchSettings.window = "month"
      binding.year.id -> searchSettings.window = "year"
      binding.all.id -> searchSettings.window = "all"
    }
    sendEvent(SettingsEvent.saveSettings(searchSettings))
  }
}