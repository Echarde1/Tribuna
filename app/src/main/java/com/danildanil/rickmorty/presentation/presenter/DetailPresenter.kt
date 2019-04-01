package com.danildanil.rickmorty.presentation.presenter

import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.repository.CharactersDataSource
import com.danildanil.rickmorty.di.module.RepositoryModule.Repo
import com.danildanil.rickmorty.di.subcomponent.screen.ActivityScreenComponent.CharId
import com.danildanil.rickmorty.presentation.base.BasePresenter
import com.danildanil.rickmorty.presentation.view.DetailMvpView
import io.reactivex.Completable
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    @CharId private val id: Long?,
    @Repo private val repository: CharactersDataSource
) : BasePresenter<DetailMvpView>() {

    private var isFavourite = false

    override fun onAttach(view: DetailMvpView) {
        super.onAttach(view)
        getDetails()
    }

    private fun getDetails() {
        Singles.zip(
            repository.loadChar(id ?: 1),
            repository.checkFavourite(id ?: 1)
        ) { character, list ->
            {
                isFavourite = list.isNotEmpty()
                view?.setData(character[0])
                view?.favourite(isFavourite)
            }
        }
            .subscribeOnIoToMain()
            .subscribe(
                { it.invoke() },
                {}
            )
            .addToPresenter()
    }

    fun onFavouriteClick() {
        (if (isFavourite) {
            isFavourite = false
            Completable.fromAction { repository.deleteFavourite(id ?: 1) }
        } else {
            isFavourite = true
            Completable.fromAction { repository.insertFavourite(Favourite(charId = id ?: 1)) }
        }).apply {
            this
                .subscribeOnIoToMain()
                .subscribe(
                    { view?.favourite(isFavourite) },
                    {}
                )
                .addToPresenter()
        }
    }
}