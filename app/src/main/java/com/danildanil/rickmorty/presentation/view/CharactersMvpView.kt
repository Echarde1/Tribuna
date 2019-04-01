package com.danildanil.rickmorty.presentation.view

import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.ui.base.MvpView

interface CharactersMvpView : MvpView {
    fun animateList()
    fun setCharacters(list: MutableList<Response.Character>)
}