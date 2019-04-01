package com.danildanil.rickmorty.presentation.base

import android.arch.lifecycle.LifecycleObserver
import com.danildanil.rickmorty.ui.base.MvpView

interface MvpPresenter<V : MvpView> : LifecycleObserver {

  fun onAttach(view: V)

  fun onDetach()

  fun onBackCommandClick()
}