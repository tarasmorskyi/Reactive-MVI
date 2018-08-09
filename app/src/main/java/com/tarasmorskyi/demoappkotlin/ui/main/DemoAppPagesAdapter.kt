package com.tarasmorskyi.demoappkotlin.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.PageItemBinding
import com.tarasmorskyi.demoappkotlin.di.ActivityScope
import com.tarasmorskyi.demoappkotlin.model.Page
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


@ActivityScope
class DemoAppPagesAdapter @Inject constructor(
    private val inflater: LayoutInflater) : RecyclerView.Adapter<DemoAppPagesAdapter.ContactViewHolder>() {

  private val onClick = PublishSubject.create<Page>()
  private var pages: List<Page>

  val clicks: Flowable<Page>
    get() = onClick.toFlowable(BackpressureStrategy.LATEST)

  init {
    pages = emptyList()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
    val binding = DataBindingUtil.inflate<PageItemBinding>(inflater, R.layout.page_item, parent,
        false)
    return ContactViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    holder.setPage(pages!![position])
  }

  override fun onViewDetachedFromWindow(holder: ContactViewHolder) {
    super.onViewDetachedFromWindow(holder)
    holder.unbind()
  }

  override fun getItemCount(): Int {
    return pages!!.size
  }

  fun setItems(@NonNull pages: List<Page>) {
    this.pages = pages
    notifyDataSetChanged()
  }

  inner class ContactViewHolder(
      private val binding: PageItemBinding) : RecyclerView.ViewHolder(
      binding.getRoot()), View.OnClickListener {
    lateinit private var page: Page

    fun setPage(page: Page) {
      this.page = page
      binding.title.text = page.title

      if(page.images.size > 0) {
        Glide
            .with(binding.photo.context)
            .load(page.images[0].link)
            .into(binding.photo)
      }else
        Glide
            .with(binding.photo.context)
            .load(page.link)
            .into(binding.photo)


      binding.photo.setOnClickListener(this)
    }

    fun unbind() {
      binding.getRoot().setOnClickListener(null)
    }

    override fun onClick(view: View) {
      when (view.id) {
        R.id.photo -> onClick.onNext(page)
      }
    }
  }
}