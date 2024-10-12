package com.sujeong.newsfeed.util.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.sujeong.newsfeed.R

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.clear() = Glide.with(context).clear(this)

fun ImageView.loadImage(
    url: Any?,
    cornerDP: Int = 0,
    @DrawableRes errorPlaceHolder: Int = R.drawable.ic_image
) {
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.with(factory))
        .error(ContextCompat.getDrawable(context, errorPlaceHolder))
        .transform(
            CenterCrop(),
            RoundedCorners(cornerDP.toPx())
        ).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImage.scaleType = ImageView.ScaleType.CENTER
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)
}