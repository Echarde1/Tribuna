package com.danildanil.rickmorty.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.danildanil.rickmorty.App
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import com.danildanil.rickmorty.util.showToast
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseFragment: Fragment(), MvpView {

  private val component: ScreensComponent = App.component.screensComponent()

  /** Идентификатор разметки */
  @get:LayoutRes
  protected abstract val layoutId: Int

  @Inject lateinit var router: Router

  private lateinit var unbinder: Unbinder

  protected val baseActivity: MvpView?
    get() = activity as? MvpView

  override fun onAttach(context: Context?) {
    injectScreen(component)
    super.onAttach(context)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(layoutId, container, false)
    unbinder = ButterKnife.bind(this, view)

    return view
  }

  override fun onDestroyView() {
    unbinder.unbind()
    super.onDestroyView()
  }

  open fun onBackPressed() {
    router.exit()
  }

  override fun showMessage(message: String) = baseActivity?.showMessage(message) ?: showToast(message)

  override fun showMessage(resId: Int) = baseActivity?.showMessage(resId) ?: showToast(resId)

  open fun injectScreen(component: ScreensComponent) {}
}