package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.FragmentGalleryBinding
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.BaseFragment
import com.tarasmorskyi.demoappkotlin.ui.base.CustomDialogFragmentEventBased.Callback
import com.tarasmorskyi.demoappkotlin.ui.main.DemoAppPagesAdapter
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryUiModel.Companion.LIKE_DIALOG
import com.tarasmorskyi.demoappkotlin.ui.main.gallery.GalleryUiModel.Companion.LOADED
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


class GalleryFragment: BaseFragment<GalleryEvent, GalleryUiModel>(), GalleryView, Callback<GalleryEvent> {

  @Inject
  internal lateinit var presenter: GalleryPresenter
  @Inject
  internal lateinit var adapter: DemoAppPagesAdapter

  private lateinit var clicksStream: Disposable

  lateinit var binding: FragmentGalleryBinding

  companion object {
    fun newInstance(): GalleryFragment {
      return GalleryFragment()
    }
  }

  override fun sendEvent(event: GalleryEvent) {
    presenter.event(event)
  }
  override fun eventConfirmed(event: GalleryEvent?) {
    if (event != null) {
      sendEvent(event)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

    binding.list.setAdapter(adapter)
    clicksStream = adapter.clicks
        .map({ GalleryEvent.onListClicked(it) })
    .toObservable().subscribe({ sendEvent(it) })
    return binding.getRoot()
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
    presenter.event(GalleryEvent.onLoaded())
  }

  override fun onDetach() {
    super.onDetach()
    presenter.detach()
    if (!clicksStream.isDisposed)
      clicksStream.dispose()
  }

  override fun render(uiModel: GalleryUiModel) {
    when (uiModel.model) {
      LOADED -> adapter.setItems(uiModel.pages)
      LIKE_DIALOG -> likePost(uiModel.page)
    }
    super.render(uiModel)
  }

  private fun likePost(page: Page) {
    val fragment = LikeDialog.newInstance(
        GalleryEvent.onLoaded(), page)
    fragment.setCallback(this)
    fragment.show(childFragmentManager, "Dialog")
  }

  override fun onNext(homeUiModel: GalleryUiModel) {
    render(homeUiModel)
  }
}