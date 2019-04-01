package com.danildanil.rickmorty.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import butterknife.BindView
import com.danildanil.rickmorty.R
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import com.danildanil.rickmorty.presentation.view.SplashMvpView
import com.danildanil.rickmorty.ui.Screens
import com.danildanil.rickmorty.ui.base.BaseFragment

class SplashFragment : BaseFragment(), SplashMvpView {

    override val layoutId: Int = R.layout.fragment_splash

    @BindView(R.id.iv_logo) lateinit var splashLogo: ImageView

    private val sharedTransitionAnim by lazy {
        TransitionSet().addTransition(ChangeBounds().apply {
            duration = TRANSITION_DURATION
            interpolator = DecelerateInterpolator()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashLogo.apply {
            tag = SPLASH_LOGO_INT_TAG
            transitionName = LOGO_TRANSITION
        }

        Handler().postDelayed(::toChars, SPLASH_DURATION)
    }

    private fun toChars() {
        val charsFragment = (Screens.CharsFragmentScreen.fragment as CharactersFragment).apply {
            sharedElementEnterTransition = sharedTransitionAnim
            setAnimationDestinationId(splashLogo.tag as Int)
        }

        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, charsFragment)
                .addSharedElement(splashLogo, LOGO_TRANSITION)
                .commitNow()
    }

    override fun injectScreen(component: ScreensComponent) = component.activity().build().inject(this)

    companion object {
        private const val SPLASH_DURATION = 1500L
        private const val SPLASH_LOGO_INT_TAG = 228
        const val LOGO_TRANSITION = "logo_transition"
        private const val TRANSITION_DURATION = 900L
    }
}