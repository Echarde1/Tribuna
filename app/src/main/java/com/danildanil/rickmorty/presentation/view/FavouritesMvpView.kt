package com.danildanil.rickmorty.presentation.view

import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.ui.base.MvpView

interface FavouritesMvpView : MvpView {

    fun setData(list: List<Response.Character>)
}