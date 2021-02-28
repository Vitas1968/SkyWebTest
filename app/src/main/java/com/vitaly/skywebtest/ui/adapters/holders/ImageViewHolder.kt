package com.vitaly.skywebtest.ui.adapters.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.skywebtest.R
import com.vitaly.skywebtest.model.entity.ImageDataClass
import com.vitaly.skywebtest.utils.loadImage
import kotlinx.android.synthetic.main.item_image_card.view.*

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(photo: ImageDataClass?) {
        with(itemView) {
            photo?.let { photo ->
                loadImage(photo.download_url, image)
                author_photo.text = photo.author
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_image_card, parent, false)
            return ImageViewHolder(view)
        }
    }
}