package com.danildanil.rickmorty.presentation.presenter

import com.danildanil.rickmorty.data.repository.CharactersDataSource
import com.danildanil.rickmorty.di.module.RepositoryModule.Repo
import com.danildanil.rickmorty.presentation.base.BasePresenter
import com.danildanil.rickmorty.presentation.view.FavouritesMvpView
import com.danildanil.rickmorty.ui.Screens
import javax.inject.Inject

class FavouritesPresenter @Inject constructor(
    @Repo private val repository: CharactersDataSource
): BasePresenter<FavouritesMvpView>() {

    override fun onAttach(view: FavouritesMvpView) {
        super.onAttach(view)
        getFavourites()
    }

    private fun getFavourites() {
        repository.loadFavouritesList()
            .subscribeOnIoToMain()
            .subscribe(
                { view?.setData(it) },
                {}
            )
            .addToPresenter()
    }

    fun navigateDetail(id: Long) = router.navigateTo(Screens.DetailFragmentScreen(id))
}