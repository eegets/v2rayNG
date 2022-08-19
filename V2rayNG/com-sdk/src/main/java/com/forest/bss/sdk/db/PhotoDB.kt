package com.forest.bss.sdk.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Desc
 *
 * Created by zhangzhixiang on 3/22/21 5:17 PM
 */
@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class PhotoDB : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}