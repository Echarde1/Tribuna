package com.danildanil.rickmorty.ui.fragment

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import butterknife.BindView
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danildanil.rickmorty.R
import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.di.subcomponent.ScreensComponent
import com.danildanil.rickmorty.presentation.presenter.DetailPresenter
import com.danildanil.rickmorty.presentation.view.DetailMvpView
import com.danildanil.rickmorty.ui.base.BaseFragment
import com.danildanil.rickmorty.util.Status.*
import javax.inject.Inject

class DetailFragment : BaseFragment(), DetailMvpView {

    override val layoutId: Int = R.layout.fragment_detail

    @BindView(R.id.iv_avatar) lateinit var avatar: ImageView
    @BindView(R.id.tv_name) lateinit var name: TextView
    @BindView(R.id.tv_status) lateinit var statusText: TextView
    @BindView(R.id.iv_status) lateinit var statusImg: ImageView
    @BindView(R.id.iv_favourite) lateinit var favourite: ImageView

    @Inject lateinit var presenter: DetailPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun setData(item: Response.Character) {
        Glide.with(this)
            .load(item.imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_rick_sanchez))
            .into(avatar)

        name.text = item.name
        statusText.text = item.status

        resources.apply {
            when (item.status) {
                getString(ALIVE.st) -> changeStatus(ALIVE.st, R.drawable.ic_alive)
                getString(DEAD.st) -> changeStatus(DEAD.st, android.R.drawable.ic_delete)
                getString(UNKNOWN.st).toLowerCase() -> changeStatus(UNKNOWN.st, R.drawable.ic_unknown)
            }
        }
    }

    private fun changeStatus(@StringRes st: Int, @DrawableRes dr: Int) {
        statusText.text = resources.getString(st)
        statusImg.setImageDrawable(resources.getDrawable(dr, null))
    }

    override fun favourite(isFavourite: Boolean) {
        val onStatus = resources.getDrawable(android.R.drawable.btn_star_big_on, null)
        val offStatus = resources.getDrawable(android.R.drawable.btn_star_big_off, null)

        if (isFavourite) {
            favourite.setImageDrawable(onStatus)
        } else {
            favourite.setImageDrawable(offStatus)
        }
    }

    @OnClick(R.id.iv_favourite)
    fun onFavouriteClick() {
        presenter.onFavouriteClick()
    }

    override fun injectScreen(component: ScreensComponent) {
        val id = arguments!!.getLong(ARG_ID)
        component.activity().withId(id).build().inject(this)
    }

    companion object {
        private val ARG_ID = "ags:id"

        fun newInstance(id: Long): DetailFragment = DetailFragment().apply {
            arguments = bundleOf(
                ARG_ID to id
            )
        }
    }
}