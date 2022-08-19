//@file:JvmName("ImageLoader")
//
//package com.forest.bss.sdk.ext
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.drawable.Drawable
//import android.widget.ImageView
//import androidx.annotation.DrawableRes
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.DecodeFormat
//import com.bumptech.glide.request.RequestListener
//import com.bumptech.glide.request.target.SimpleTarget
//import com.bumptech.glide.request.transition.Transition
//import com.forest.bss.sdk.R
//import com.forest.bss.sdk.log.logger
//import java.io.File
//import kotlin.concurrent.thread
//
///**
// * Created by wangkai on 2021/01/28 13:46
// * Desc glide图片加载
// */
//
///**
// * 安全加载图片
// *   * imageView为null不加载
// *   * imageUrl为null不加载
// *   * context为null且对应的Activity实例销毁不加载
// */
//fun ImageView?.loadImageSafely(imageUrl: String?) {
//    nonNull(this) { view ->
//        safeRun {
//            Glide.with(view).load(imageUrl).format(DecodeFormat.PREFER_RGB_565)
//                    .fallback(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .error(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .placeholder(R.mipmap.icon_image_none)//图片加载失败后，显示的图片
//                    .into(view)
//            logger {
//                "loadImageSafely real load imageView: $this, imageUrl: $imageUrl"
//            }
//        }
//    }
//    logger {
//        "loadImageSafely real load imageView: $this, imageUrl: $imageUrl"
//    }
//}
//
//
//fun ImageView?.loadImageSafely(@DrawableRes drawableId: Int) {
//    nonNull(this) { view ->
//        safeRun {
//            Glide.with(view).load(drawableId).format(DecodeFormat.PREFER_RGB_565)
//                    .fallback(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .error(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .placeholder(R.mipmap.icon_image_none)//图片加载失败后，显示的图片
//                    .into(view)
//            logger {
//                "loadImageSafely real load imageView: $this, imageUrl: $drawableId"
//            }
//        }
//    }
//    logger {
//        "loadImageSafely real load imageView: $this, imageUrl: $drawableId"
//    }
//}
//
//fun ImageView?.loadImageSafely(@DrawableRes drawableId: Int, width: Float, height: Float) {
//    nonNull(this) { view ->
//        safeRun {
//            Glide.with(view).load(drawableId).format(DecodeFormat.PREFER_RGB_565)
//                    .fallback(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .error(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .placeholder(R.mipmap.icon_image_none)//图片加载失败后，显示的图片
//                    .override(dp2px(width), dp2px(height))
//                    .into(view)
//            logger {
//                "loadImageSafely real load imageView: $this, imageUrl: $drawableId"
//            }
//        }
//    }
//    logger {
//        "loadImageSafely real load imageView: $this, imageUrl: $drawableId"
//    }
//}
//
///**
// * 安全加载图片
// *   * imageView为null不加载
// *   * imageUrl为null不加载
// *   * context为null且对应的Activity实例销毁不加载
// */
//fun ImageView?.loadImageSafely(imagePath: File?) {
//    nonNull(this) { view ->
//        safeRun {
//            Glide.with(view).load(imagePath).format(DecodeFormat.PREFER_RGB_565)
//                    .fallback(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .error(R.mipmap.icon_image_none)//url为空的时候,显示的图片
//                    .placeholder(R.mipmap.icon_image_none)//图片加载失败后，显示的图片
//                    .into(view)
//            logger {
//                "loadImageSafely real load imageView: $this, imageUrl: $imagePath"
//            }
//        }
//    }
//    logger {
//        "loadImageSafely real load imageView: $this, imageUrl: $imagePath"
//    }
//}
//
///**
// * 安全加载图片
// *   * imageView为null不加载
// *   * imageUrl为null不加载
// *   × listener为null不加载
// *   * context为null且对应的Activity实例销毁不加载
// *
// */
//@JvmOverloads
//fun ImageView?.loadImageSafely(imageUrl: String?, listener: RequestListener<Drawable>) {
//    nonNull(this, imageUrl) { view, url ->
//        safeRun {
//            Glide.with(view).load(url).addListener(listener).into(view)
//            logger {
//                "loadImageSafely real load imageView: $this, imageUrl: $imageUrl"
//            }
//        }
//    }
//    logger {
//        "loadImageSafely real load imageView: $this, imageUrl: $imageUrl"
//    }
//}
//
//@JvmOverloads
//fun String.loadBitmapSafely(context: Context, loadFailed: ((Drawable?) -> Unit)? = null, loadSucceeded: ((Bitmap) -> Unit)? = null) {
//    Glide.with(context)
//            .asBitmap()
//            .load(this)
//            .into(object : SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    loadSucceeded?.invoke(resource)
//                    logger {
//                        "loadBitmapSafely resourceReady bitmap=$resource"
//                    }
//                }
//
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    super.onLoadFailed(errorDrawable)
//                    loadFailed?.invoke(errorDrawable)
//                    logger {
//                        "loadBitmapSafely onLoadFailed errorDrawable=$errorDrawable"
//                    }
//                }
//            })
//}
//
///**
// * 清理Glide缓存
// */
//fun clearGlideCache(context: Context) {
//    clearGlideDiskCache(context)
//}
//
//private fun clearGlideDiskCache(context: Context) {
//    runOnIOThread {
//        Glide.get(context.applicationContext).clearMemory()
//        Glide.get(context.applicationContext).clearDiskCache()
//    }
//    logger {
//        "Glide磁盘缓存清理完成"
//    }
//}
