package com.forest.bss.sdk.ext

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import com.forest.bss.sdk.log.logger
import com.forest.bss.sdk.util.PathUtils
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

/**
 * Created by wangkai on 2021/03/03 17:09

 * Desc TODO
 */

fun Uri.toFile(tempPhotoDir: String, context: Context): File? = when (scheme) {
    ContentResolver.SCHEME_FILE -> {
        logger {
            "Uri toFile ContentResolver.SCHEME_FILE path: $path"
        }
        File(requireNotNull(path))
    }
    ContentResolver.SCHEME_CONTENT -> {
        context.contentResolver.query(this, null, null, null, null).use {
            val file = it?.use {
                if (it.moveToFirst()) {
                    if (isAndroidQ) {
                        //保存到本地
                        val ois = getValueSafely {
                            context.contentResolver.openInputStream(this)
                        }
                        val displayName = getValueSafely {
                            it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        }
                        ois?.let {
                            var file : File? = null
                            try {
                                file = File(tempPhotoDir, "${Random.nextInt(0, 9999)}$displayName")
                            } catch (throwable: Throwable) {
                                throwable.printStackTrace()
                            }
                            FileOutputStream(file).use {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    android.os.FileUtils.copy(ois, it)
                                }
                            }
                            file
                        }
                    } else {
                        //直接转换
                        val file = getValueSafely { File(PathUtils.getPath(context, this)) }
                        file
//                        File(it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA)))
                    }
                } else null
            }
            logger {
                "Uri toFile ContentResolver.SCHEME_CONTENT path: ${file?.absolutePath}"
            }
            file
        }
    }
    else -> null
}