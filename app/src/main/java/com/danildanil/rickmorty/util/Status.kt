package com.danildanil.rickmorty.util

import android.support.annotation.StringRes
import com.danildanil.rickmorty.R


enum class Status (@StringRes val st: Int) {
    ALIVE(R.string.chars_status_alive),
    DEAD(R.string.chars_status_dead),
    UNKNOWN(R.string.chars_status_unknown)
}