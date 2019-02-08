package com.tarasmorskyi.demoappkotlin.ui.main.my_gallery

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.FragmentMyGalleryBinding
import com.tarasmorskyi.demoappkotlin.ui.base.BaseFragment
import com.tarasmorskyi.demoappkotlin.ui.main.DemoAppPagesAdapter
import timber.log.Timber
import javax.inject.Inject


class MyGalleryFragment: BaseFragment<MyGalleryEvent, MyGalleryUiModel>(), MyGalleryView{

  @Inject
  internal
  lateinit var eventManager: MyGalleryEventManager
  @Inject
  internal lateinit var adapter: DemoAppPagesAdapter

  lateinit var binding: FragmentMyGalleryBinding

  companion object {
    fun newInstance(): MyGalleryFragment {
      return MyGalleryFragment()
    }
  }

  override fun sendEvent(event: MyGalleryEvent) {
    eventManager.event(event)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_gallery, container, false)
    binding.list.adapter = adapter
    return binding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    setDefaults(eventManager.attach(this)).subscribe(this)
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    Timber.d("onViewStateRestored() called  with: savedInstanceState = [%s]", savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    eventManager.event(MyGalleryEvent.Loaded)
  }

  override fun onDetach() {
    super.onDetach()
    eventManager.detach()
  }

  override fun render(uiModel: MyGalleryUiModel) {
    when (uiModel) {
      is MyGalleryUiModel.Loaded -> adapter.setItems(uiModel.pages)
    }
  }

  override fun onNext(homeUiModel: MyGalleryUiModel) {
    render(homeUiModel)
  }
}