package com.forest.bss.sdk.db

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.forest.bss.sdk.dao.UserDao
import com.forest.bss.sdk.ext.appContext


/**
 * Desc
 *
 * Created by zhangzhixiang on 3/22/21 6:19 PM
 */
object DbManager {

    const val DATABASE_NAME = "YQ_PHOTOS"

    private val db: PhotoDB by lazy {
        Room.databaseBuilder(appContext, PhotoDB::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE department "
                    + " ADD COLUMN phone_num TEXT")
        }
    }

    fun addPhoto(photo: Photo) {
        db.photoDao().insertPhoto(photo)
    }

    fun queryPhotos(userId: String): List<Photo> {
        return db.photoDao().getToUploadPhotos(userId)
    }

    fun deleteByLocalPath(localPath: String) {
        db.photoDao().deleteByLocalPath(localPath)
    }

    fun setShouldDelete(localPath: String) {
        db.photoDao().setShouldDelete(localPath)
    }

    fun deleteUploadedPhotosThisTour(localTourId: String) {
        db.photoDao().deleteUploadedPhotosThisTour(localTourId)
    }

    fun deleteAllPhotosThisTour(localTourId: String) {
        db.photoDao().deleteAllPhotosThisTour(localTourId)
    }

    fun getAllPhotosThisTour(localTourId: String): List<String> {
        return db.photoDao().getAllPhotosThisTour(localTourId)
    }

    fun getUploadedPhotosThisTour(localTourId: String): List<String> {
        return db.photoDao().getUploadedPhotosThisTour(localTourId)
    }

    /**
     * 获取 "shouldDelete = 1" 所有照片上传成功但是未完成巡店的表记录
     */
    fun getUploadSuccessTourCancelList() : List<String> {
        return db.photoDao().getUploadSuccessTourCancelList()
    }

    /**
     * 删除 "shouldDelete = 1" 所有照片上传成功但是未完成巡店的表记录
     */
    fun deleteUploadSuccessTourCancelList() {
        return db.photoDao().deleteUploadSuccessTourCancelList()
    }

    fun getUnUploadedPhotoCount(): Int {
        val id = UserDao.getId()
        id?.let {
            return db.photoDao().getUploadedPhotosCount(id)
        }
        return 0
    }

}