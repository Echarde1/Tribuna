package com.danildanil.rickmorty.ui

import android.support.v4.app.Fragment
import com.danildanil.rickmorty.ui.fragment.CharactersFragment
import com.danildanil.rickmorty.ui.fragment.DetailFragment
import com.danildanil.rickmorty.ui.fragment.FavouritesFragment
import com.danildanil.rickmorty.ui.fragment.SplashFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object SplashFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SplashFragment()
    }

    object CharsFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = CharactersFragment()
    }

    data class DetailFragmentScreen(val id: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = DetailFragment.newInstance(id)
    }

    object FavouritesFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = FavouritesFragment()
    }
}