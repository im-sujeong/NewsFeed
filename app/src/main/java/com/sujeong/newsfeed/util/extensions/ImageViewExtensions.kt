package com.sujeong.newsfeed.util.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.util.Util
import com.sujeong.newsfeed.R
import java.io.File
import java.io.FileOutputStream
import java.security.DigestException
import java.security.MessageDigest

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.clear() = Glide.with(context).clear(this)

fun ImageView.loadImage(
    url: String?,
    cornerDP: Int = 0,
    @DrawableRes errorPlaceHolder: Int = R.drawable.ic_image
) {
    //캐시를 사용하면 캐시 삭제 후 이미지를 가져올 수 없으므로 파일 dir에 저장.
    val imageKey = url?.let {
        toHexStringKey(url)
    }

    val cacheFile = imageKey?.let {
        File(
            context.filesDir.absolutePath,
            imageKey
        )
    }

    if(cacheFile?.exists() == true) {
        Glide.with(context)
            .load(cacheFile)
            .transition(DrawableTransitionOptions.with(factory))
            .transform(
                CenterCrop(),
                RoundedCorners(cornerDP.toPx())
            )
            .into(this)

        return
    }

    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
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
                if(cacheFile?.exists() == false) {
                    cacheFile.createNewFile()

                    FileOutputStream(cacheFile).use {
                        //이미지 용량 문제도 고민을 해봐야 할 듯.
                        resource.toBitmap().compress(
                            Bitmap.CompressFormat.JPEG,
                            100,
                            it
                        )
                    }
                }

                return false
            }

        })
        .into(this)
}

private fun toHexStringKey(str: String): String {
    val hash: ByteArray

    try {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(str.toByteArray())
        hash = md.digest()
    } catch (e: CloneNotSupportedException) {
        throw DigestException("couldn't make digest of partial content");
    }

    return "${Util.sha256BytesToHex(hash)}.jpg"
}