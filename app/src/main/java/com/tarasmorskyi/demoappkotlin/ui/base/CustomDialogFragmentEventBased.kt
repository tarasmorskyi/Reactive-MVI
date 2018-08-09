package com.tarasmorskyi.demoappkotlin.ui.base

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarasmorskyi.demoappkotlin.R
import com.tarasmorskyi.demoappkotlin.databinding.FragmentDialogBinding
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class CustomDialogFragmentEventBased<E : BaseEvent> : DialogFragment(), HasSupportFragmentInjector {
  protected var event: E? = null
  private lateinit var binding: FragmentDialogBinding
  private var callback: Callback<E>? = null


  @Inject
  lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>


  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return childFragmentInjector
  }

  private val args: E?
    get() = arguments!!.getParcelable(EVENT_CASE)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    event = args
    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme_Dialog)
  }

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  fun setCallback(callback: Callback<E>) {
    this.callback = callback
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog,
        container, false)

    setBackground(binding.root)
    return binding.getRoot()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return object : Dialog(activity!!, theme) {
      override fun onBackPressed() {
        dismiss()
      }
    }
  }

  protected fun getMessageWithDrawable(@StringRes message: Int,
      @DrawableRes drawableRes: Int): SpannableString {
    val src = getString(message)
    val str = SpannableString(src)
    val d = ContextCompat.getDrawable(context!!, drawableRes)
    d!!.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
    val span = ImageSpan(d, ImageSpan.ALIGN_BOTTOM)
    str.setSpan(span, str.toString().indexOf("@"), str.toString().indexOf("@") + 1,
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return str
  }

  fun sendEvent(event: E?) {
    if (callback != null) {
      callback!!.eventConfirmed(event)
    }
  }

  fun setBackground(root: View) {
    val handler = Handler()
    handler.postDelayed({
      if (context != null) {
        val colorFrom = ContextCompat.getColor(context!!, R.color.transparent)
        val colorTo = ContextCompat.getColor(context!!, R.color.transparent_25)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 150 // milliseconds
        colorAnimation.addUpdateListener { animator ->
          root.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
      }
    }, 750)
  }

  interface Callback<E> {
    fun eventConfirmed(event: E?)
  }

  companion object {
    val EVENT_CASE = "event"
  }
}
