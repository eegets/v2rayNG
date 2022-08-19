package com.forest.bss.sdk.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

/**
 * Desc
 *
 * Created by zhangzhixiang on 3/22/21 5:04 PM
 */
@Dao
interface PhotoDao {

    @Insert
    fun insertPhoto(photo: Photo?)

    @Insert
    fun insertPhotos(moviesList: List<Photo>?)

    @Query("SELECT * FROM Photo WHERE userId=:userId")
    fun getPhotosById(userId: String): LiveData<List<Photo>?>

    @Update
    fun update(photo: Photo?)

    @Delete
    fun delete(Photo: Photo?)

    /**
     * 根据本地生成的巡店id批量删除（巡店失败的照片记录）
     */
    @Query("DELETE from Photo where localTourId=:localTourId ")
    fun deleteByLocalTourId(localTourId: String)

    /**
     * 根据objectKey删除
     */
    @Query("DELETE from Photo where objectKey=:objectKey ")
    fun deleteByObjectKey(objectKey: String)

    /**
     * 查询分组后的照片
     */
    @Query("SELECT * FROM Photo WHERE userId=:userId AND shouldDelete=0")
    fun getToUploadPhotos(userId: String): List<Photo>

    /**
     * 根据本地路径删除照片记录·
     */
    @Query("DELETE FROM Photo WHERE localPath=:path")
    fun deleteByLocalPath(path: String)

    /**
     * 将指定照片设置为的shouldDelete字段设置为1
     */
    @Query("UPDATE Photo SET shouldDelete=1 WHERE localPath=:path")
    fun setShouldDelete(path: String)

    /**
     *删除本次巡店中，上传成功的照片
     */
    @Query("DELETE FROM Photo WHERE localTourId=:localTourId AND shouldDelete=1")
    fun deleteUploadedPhotosThisTour(localTourId: String)

    /**
     *获取本次巡店中，上传成功的照片
     */
    @Query("SELECT localPath FROM Photo WHERE localTourId=:localTourId AND shouldDelete=1")
    fun getUploadedPhotosThisTour(localTourId: String): List<String>

    /**
     * 获取 "shouldDelete = 1" 所有照片上传成功但是未完成巡店的表记录
     */
    @Query("SELECT localPath FROM Photo WHERE shouldDelete=1")
    fun getUploadSuccessTourCancelList(): List<String>

    /**
     *获取当前用户待传照片总数
     */
    @Query("SELECT COUNT(*) FROM Photo WHERE userId=:userId AND shouldDelete=0")
    fun getUploadedPhotosCount(userId: String): Int

    /**
     *删除本次巡店中的所有照片
     */
    @Query("DELETE FROM Photo WHERE localTourId=:localTourId")
    fun deleteAllPhotosThisTour(localTourId: String)

    /**
     * 删除 "shouldDelete = 1" 所有照片上传成功但是未完成巡店的表记录
     */
    @Query("DELETE FROM Photo WHERE shouldDelete=1")
    fun deleteUploadSuccessTourCancelList()


    /**
     *获取本次巡店中的所有照片路径
     */
    @Query("SELECT localPath FROM Photo WHERE localTourId=:localTourId")
    fun getAllPhotosThisTour(localTourId: String): List<String>

}