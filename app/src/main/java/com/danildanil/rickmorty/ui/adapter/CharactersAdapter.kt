package com.danildanil.rickmorty.ui.adapter

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danildanil.rickmorty.R
import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.util.Status.*
import com.danildanil.rickmorty.util.inflate


class CharactersAdapter(
    private val detailListener: (Long) -> Unit = {},
    private val nextPageListener: () -> Unit = {}
) : RecyclerView.Adapter<CharactersAdapter.CharItemHolder>() {

    private var items: MutableList<Response.Character> = mutableListOf()

    fun setData(list: List<Response.Character>) {
        if (items.containsAll(list)) return

        val oldData = items.toList()

        items.addAll(list)

        DiffUtil
            .calculateDiff(DiffCallback(items, oldData))
            .dispatchUpdatesTo(this)
    }

    fun removeItem(list: List<Response.Character>) {
        val oldData = items.toList()
        items.clear()
        items = list.toMutableList()

        DiffUtil
            .calculateDiff(DiffCallback(items, oldData))
            .dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): CharItemHolder =
            CharItemHolder(container.inflate(R.layout.item_char), detailListener)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharItemHolder, position: Int) {
        holder.bindItem(items[position])

        if (position == items.size - 5) {
            nextPageListener()
        }
    }

    class CharItemHolder(itemView: View, listener: (Long) -> Unit) :
            RecyclerView.ViewHolder(itemView) {

        private lateinit var item: Response.Character
        @BindView(R.id.item_container) lateinit var container: CardView
        @BindView(R.id.iv_avatar) lateinit var avatar: ImageView
        @BindView(R.id.tv_name) lateinit var name: TextView
        @BindView(R.id.status) lateinit var statusText: TextView
        @BindView(R.id.iv_status) lateinit var statusImg: ImageView

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener(item.id)
                }
            }

            ButterKnife.bind(this, itemView)
        }

        fun bindItem(item: Response.Character) {
            this.item = item

            Glide.with(itemView)
                    .load(item.imageUrl)
                    .apply(RequestOptions().placeholder(R.drawable.ic_rick_sanchez))
                    .into(avatar)

            name.text = item.name
            itemView.resources.apply {
                when (item.status) {
                    getString(ALIVE.st) -> changeStatus(ALIVE.st, R.drawable.ic_alive)
                    getString(DEAD.st) -> changeStatus(DEAD.st, android.R.drawable.ic_delete)
                    getString(UNKNOWN.st).toLowerCase() -> changeStatus(UNKNOWN.st, R.drawable.ic_unknown)
                }
            }
        }

        private fun changeStatus(@StringRes st: Int, @DrawableRes dr: Int) {
            statusText.text = itemView.resources.getString(st)
            statusImg.setImageDrawable(itemView.resources.getDrawable(dr, null))
        }
    }

    private inner class DiffCallback(
        private val newItems: List<Response.Character>,
        private val oldItems: List<Response.Character>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return newItem.id == oldItem.id
                    && newItem.imageUrl == oldItem.imageUrl
                    && newItem.status == oldItem.status
                    && newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return newItem == oldItem
        }
    }
}