package com.danildanil.rickmorty.ui

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import androidx.core.widget.toast
import com.danildanil.rickmorty.App
import com.danildanil.rickmorty.R
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import com.danildanil.rickmorty.ui.base.BaseFragment
import com.danildanil.rickmorty.ui.base.MvpView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class AppActivity : AppCompatActivity(), MvpView {

    val layoutId: Int = R.layout.layout_container

    private val component: ScreensComponent = App.component.screensComponent()

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
        override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction?
        ) {
            fragmentTransaction?.setCustomAnimations(R.anim.slide_in_right, android.R.anim.fade_out, android.R.anim.fade_in, R.anim.slide_out_right)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectScreen(component)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, Screens.SplashFragmentScreen.fragment)
                    .commitNow()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun showMessage(message: String) = toast(message).show()

    override fun showMessage(@StringRes resId: Int) = toast(resId).show()

    private fun injectScreen(component: ScreensComponent) = component.activity().build().inject(this)
}
