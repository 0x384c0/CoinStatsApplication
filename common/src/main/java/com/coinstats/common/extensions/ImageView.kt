package com.coinstats.common.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * load and show image from url
 */
fun ImageView.load(url: String?) {
    Picasso.get().load(url).into(this)
}