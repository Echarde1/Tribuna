package com.danildanil.rickmorty.presentation.presenter

import android.util.Log
import com.danildanil.rickmorty.data.repository.CharactersDataSource
import com.danildanil.rickmorty.di.module.RepositoryModule.Repo
import com.danildanil.rickmorty.presentation.base.BasePresenter
import com.danildanil.rickmorty.presentation.view.CharactersMvpView
import com.danildanil.rickmorty.ui.Screens
import com.danildanil.rickmorty.util.TAG
import javax.inject.Inject

class CharactersPresenter @Inject constructor(
    @Repo private val repository: CharactersDataSource
) : BasePresenter<CharactersMvpView>() {

    private val FIRST_PAGE = 1
    private var currentPage = 1

    override fun onAttach(view: CharactersMvpView) {
        super.onAttach(view)
        getChars()
    }

    private fun getChars() {
        repository.loadCharacters(FIRST_PAGE)
            .subscribeOnIoToMain()
            .subscribe(
                { view?.setCharacters(it.toMutableList()) },
                {}
            )
            .addToPresenter()
    }

    fun loadNextPage() {
        if (currentPage == maxPages) return
        repository.loadCharacters(++currentPage)
            .subscribeOnIoToMain()
            .subscribe(
                { view?.setCharacters(it.toMutableList()) },
                { Log.e(TAG, it.message) }
            )
            .addToPresenter()
    }

    fun navigateDetail(id: Long) = router.navigateTo(Screens.DetailFragmentScreen(id))
    fun navigateFavourites() = router.navigateTo(Screens.FavouritesFragmentScreen)

    companion object {
        var maxPages: Int = 0
    }
}