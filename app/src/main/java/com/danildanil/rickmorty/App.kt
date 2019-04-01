package com.danildanil.rickmorty

import android.app.Application
import com.danildanil.rickmorty.di.AppComponent
import com.danildanil.rickmorty.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(this)
    }

    companion object {

        lateinit var instance: App
            private set

        val component: AppComponent
                by lazy { DaggerAppComponent.builder().application(instance).build() }
    }
}