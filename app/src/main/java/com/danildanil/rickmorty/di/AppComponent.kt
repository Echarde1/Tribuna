package com.danildanil.rickmorty.di

import android.app.Application
import com.danildanil.rickmorty.App
import com.danildanil.rickmorty.di.module.*
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidModule::class,
        ApiModule::class,
        OkHttpModule::class,
        RetrofitModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun screensComponent(): ScreensComponent

    fun inject(app: App)

}