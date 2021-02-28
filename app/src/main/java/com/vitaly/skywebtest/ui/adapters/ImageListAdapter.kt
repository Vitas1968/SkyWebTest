package com.vitaly.skywebtest.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.skywebtest.model.entity.ImageDataClass
import com.vitaly.skywebtest.ui.adapters.holders.ImageViewHolder
import com.vitaly.skywebtest.ui.adapters.holders.NetworkStateViewHolder
import com.vitaly.skywebtest.utils.State


class ImageListAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<ImageDataClass, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private var networkState = State.LOADING
    private val DATA_VIEW_TYPE = 1
    private val PROGRESS_VIEW_TYPE = 2

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<ImageDataClass>() {
            override fun areItemsTheSame(
                oldItem: ImageDataClass,
                newItem: ImageDataClass
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ImageDataClass,
                newItem: ImageDataClass
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA_VIEW_TYPE -> ImageViewHolder.create(parent)
            PROGRESS_VIEW_TYPE -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DATA_VIEW_TYPE -> (holder as ImageViewHolder).bindTo(getItem(position))
            PROGRESS_VIEW_TYPE -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    fun setState(state: State) {
        networkState = state
        notifyItemChanged(super.getItemCount())
    }

    private fun hasExtraRow(): Boolean {
        return super.getItemCount() != 0 && networkState != State.DONE
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else PROGRESS_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }
}

