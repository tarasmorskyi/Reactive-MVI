package com.tarasmorskyi.demoappkotlin.ui.main.gallery

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.FragmentLikeDialogBinding
import com.tarasmorskyi.demoappkotlin.model.Page
import com.tarasmorskyi.demoappkotlin.ui.base.CustomDialogFragmentEventBased


class LikeDialog : CustomDialogFragmentEventBased<GalleryEvent>(), View.OnClickListener {

  internal lateinit var binding: FragmentLikeDialogBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_like_dialog, container,
        false)
    binding.like.setOnClickListener(this)
    binding.close.setOnClickListener(this)

    val handler = Handler()
    handler.postDelayed({
      try {
        val colorFrom = context?.let { ContextCompat.getColor(it, R.color.transparent) }
        val colorTo = context?.let { ContextCompat.getColor(it, R.color.transparent_25) }
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 150 // milliseconds
        colorAnimation.addUpdateListener { animator ->
          binding.root.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
      } catch (ignore: NullPointerException) {
      }
    }, 750)
    return binding.getRoot()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return object : Dialog(activity as Context, theme) {
      override fun onBackPressed() {
        dismissView()
      }
    }
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.like -> {
        sendEvent(GalleryEvent.Like(getPage()))
        dismissView()
      }
      R.id.close -> {
        dismissView()
      }
      else -> {
      }
    }
  }

  private fun getPage(): Page {
    arguments?.let { (it.getParcelable(PAGE) as Page?)?.let { page -> return page } }

    return Page()
  }

  fun dismissView() {
    val colorFrom = context?.let { ContextCompat.getColor(it, R.color.transparent_25) }
    val colorTo = context?.let { ContextCompat.getColor(it, R.color.transparent) }
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = 150 // milliseconds
    colorAnimation.addUpdateListener { animator ->
      binding.root.setBackgroundColor(animator.animatedValue as Int)
    }
    colorAnimation.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animator: Animator) {

      }

      override fun onAnimationEnd(animator: Animator) {
        dismiss()
      }

      override fun onAnimationCancel(animator: Animator) {

      }

      override fun onAnimationRepeat(animator: Animator) {

      }
    })
    colorAnimation.start()
  }

  companion object {
    private const val PAGE: String = "page"

    fun newInstance(page: Page): LikeDialog {
      val f = LikeDialog()
      val args = Bundle()
      args.putParcelable(PAGE, page)
      f.arguments = args
      return f
    }
  }
}

