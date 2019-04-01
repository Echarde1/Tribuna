package com.danildanil.rickmorty.util

import android.content.Context
import android.support.annotation.StringRes
import androidx.core.widget.toast

fun Context.showToast(text: String) = toast(text)

fun Context.showToast(@StringRes textResId: Int) = toast(textResId)