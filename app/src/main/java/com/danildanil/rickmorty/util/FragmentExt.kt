package com.danildanil.rickmorty.util

import android.support.annotation.StringRes
import android.support.v4.app.Fragment

fun Fragment.showToast(text: String) {
    context?.showToast(text)
}

fun Fragment.showToast(@StringRes textResId: Int) {
    context?.showToast(textResId)
}