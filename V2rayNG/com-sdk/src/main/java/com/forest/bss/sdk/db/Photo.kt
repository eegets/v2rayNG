package com.forest.bss.sdk.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.forest.bss.sdk.dao.UserDao
import com.forest.bss.sdk.ext.notEmpty

/**
 * Desc 保存图片的实体
 *
 * Created by zhangzhixiang on 3/22/21 4:38 PM
 */
@Entity
data class Photo(

        //当前用户id,只查询当前登陆用户id的待上传图片
        var userId: String,

        @PrimaryKey(autoGenerate = true)
        var keyId: Long = 0,

        @NonNull
        //图片本地存储的的路径
        var localPath: String,

        //上传oss的objectKey
        var objectKey: String,

        //网点id
        var shopId: String,

        //网点名称
        var shopName: String,

        //本地生成的巡店id，用于巡店失败后删除相关记录
        var localTourId: String,

        //照片类型（1-门头照 2-常温货架 3-冰柜货架  4-暖柜货架 5-端架 6-地堆陈列 7-特殊陈列 8-广宣照片 9-设备）
        var photoType: String,

        //是否是设备，true时-为以下两个字段赋值ActivityLifecycleCallbacksImpl
        var deviceFlag: Boolean = false,

        var deviceType: String,

        var deviceUniqCode: String,

        //巡店时间
        var tourTime: Long,

        //是否应该删除本地照片（上传成功，或 点击删除 时，将此标示置为true，完成巡店后，将这些照片删除，并从数据库中删除记录）
        var shouldDelete: Int = 0

) {

    companion object {

        fun create(path: String,
                   shopId: String,
                   shopName: String,
                   tourId: String,
                   photoType: String,
                   isDevice: Boolean,
                   deviceType: String = "",
                   deviceUniqueCode: String = ""
        ): Photo {

            val split = path.split("/")
            val fileName = split[split.size - 1]
//            val objKey = fileName.substring(4)
            val objKey = fileName

            return Photo(
                    userId = UserDao.getId().notEmpty(),
                    localPath = path,
                    objectKey = objKey,
                    shopId = shopId,
                    shopName = shopName,
                    localTourId = tourId,
                    photoType = photoType,
                    deviceFlag = isDevice,
                    deviceType = deviceType,
                    deviceUniqCode = deviceUniqueCode,
                    tourTime = System.currentTimeMillis()
            )
        }

    }

}