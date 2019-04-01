package com.danildanil.rickmorty.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.transition.TransitionSet
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.transition.addListener
import butterknife.BindView
import com.danildanil.rickmorty.R
import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import com.danildanil.rickmorty.presentation.presenter.CharactersPresenter
import com.danildanil.rickmorty.presentation.view.CharactersMvpView
import com.danildanil.rickmorty.ui.adapter.CharactersAdapter
import com.danildanil.rickmorty.ui.base.BaseFragment
import javax.inject.Inject


class CharactersFragment : BaseFragment(), CharactersMvpView {

    override val layoutId: Int = R.layout.fragment_characters

    @BindView(R.id.iv_logo) lateinit var logo: ImageView
    @BindView(R.id.rv_chars) lateinit var charsList: RecyclerView

    @Inject lateinit var presenter: CharactersPresenter

    private val adapter by lazy {
        CharactersAdapter(
            presenter::navigateDetail,
            presenter::loadNextPage
        )
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        retainInstance = true

        (sharedElementEnterTransition as? TransitionSet)?.addListener(
            onEnd = { presenter.onAttach(this) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        logo.apply {
            tag = getAnimationDestinationId()
            transitionName = SplashFragment.LOGO_TRANSITION
        }

        charsList.apply {
            setHasFixedSize(true)
            adapter = this@CharactersFragment.adapter
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favourite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.item_favourite -> {
            presenter.navigateFavourites()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun setCharacters(list: MutableList<Response.Character>) {
        adapter.setData(list)
    }

    override fun animateList() {
        AnimationUtils.loadAnimation(requireActivity(), android.R.anim.fade_in).also {
            charsList.startAnimation(it)
        }
    }

    private fun getAnimationDestinationId(): Int? = arguments?.getInt(ARG_ANIM_DESTINATION)

    fun setAnimationDestinationId(resId: Int) = arguments.apply {
        bundleOf(ARG_ANIM_DESTINATION to resId)
    }

    override fun injectScreen(component: ScreensComponent) = component.activity().build().inject(this)

    companion object {
        private const val ARG_ANIM_DESTINATION = "arg_anim_dest"
    }
}