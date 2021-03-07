package com.vitaly.skywebtest.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vitaly.skywebtest.R

fun loadImage(url: String, container: ImageView) {
    Glide.with(container.context)
        .load(url)
        .placeholder(R.drawable.ic_no_photo_vector)
        .into(container)
}