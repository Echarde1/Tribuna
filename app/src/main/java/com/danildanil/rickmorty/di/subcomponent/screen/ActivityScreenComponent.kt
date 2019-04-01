package com.danildanil.rickmorty.di.subcomponent.screen

import com.danildanil.rickmorty.ui.AppActivity
import com.danildanil.rickmorty.ui.fragment.CharactersFragment
import com.danildanil.rickmorty.ui.fragment.DetailFragment
import com.danildanil.rickmorty.ui.fragment.FavouritesFragment
import com.danildanil.rickmorty.ui.fragment.SplashFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Qualifier

@Subcomponent
interface ActivityScreenComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityScreenComponent

        @BindsInstance
        fun withId(@CharId id: Long?): Builder

    }

    fun inject(activity: AppActivity)
    fun inject(fragment: SplashFragment)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: DetailFragment)
    fun inject(fragment: FavouritesFragment)

    @Qualifier
    @MustBeDocumented
    annotation class CharId

}