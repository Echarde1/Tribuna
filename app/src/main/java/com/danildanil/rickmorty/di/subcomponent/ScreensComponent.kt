package com.danildanil.rickmorty.di.subcomponent

import com.danildanil.rickmorty.di.subcomponent.screen.ActivityScreenComponent
import dagger.Subcomponent

@Subcomponent
interface ScreensComponent {

    fun activity(): ActivityScreenComponent.Builder

}
