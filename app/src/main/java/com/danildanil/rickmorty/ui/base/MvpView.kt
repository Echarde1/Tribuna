package com.danildanil.rickmorty.ui.base

import android.support.annotation.StringRes

interface MvpView {

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)
}