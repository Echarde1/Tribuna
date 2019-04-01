package com.danildanil.rickmorty.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.danildanil.rickmorty.R
import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import com.danildanil.rickmorty.presentation.presenter.FavouritesPresenter
import com.danildanil.rickmorty.presentation.view.FavouritesMvpView
import com.danildanil.rickmorty.ui.adapter.CharactersAdapter
import com.danildanil.rickmorty.ui.base.BaseFragment
import javax.inject.Inject

class FavouritesFragment : BaseFragment(), FavouritesMvpView {

    override val layoutId: Int = R.layout.fragment_favourites

    @BindView(R.id.rv_favourites) lateinit var favouritesList: RecyclerView

    @Inject lateinit var presenter: FavouritesPresenter

    private val adapter by lazy { CharactersAdapter(
        presenter::navigateDetail
    ) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        favouritesList.apply {
            setHasFixedSize(true)
            adapter = this@FavouritesFragment.adapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun setData(list: List<Response.Character>) {
        adapter.removeItem(list)
    }

    override fun injectScreen(component: ScreensComponent) = component.activity().build().inject(this)
}