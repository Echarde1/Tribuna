package com.danildanil.rickmorty.presentation.view

import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.ui.base.MvpView

interface DetailMvpView : MvpView {
    fun setData(item: Response.Character)
    fun favourite(isFavourite: Boolean)
}