package com.vitaly.skywebtest.ui.adapters.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.skywebtest.R
import com.vitaly.skywebtest.utils.State
import kotlinx.android.synthetic.main.item_image_progress.view.*

class NetworkStateViewHolder(
    view: View,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view) {
    init {
        itemView.txt_error.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: State) {
        with(itemView) {
            txt_error.visibility = if (networkState == State.ERROR) View.VISIBLE else View.GONE
            progress_progress_bar.visibility =
                if (networkState != State.LOADING) View.VISIBLE else View.GONE

        }
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_image_progress, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }
    }
}