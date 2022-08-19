package com.forest.bss.sdk.pref

import android.content.SharedPreferences
import com.forest.bss.sdk.ext.*
import com.forest.bss.sdk.log.logger

object TouringDataSharedPref {

    /***********************************************分割线****************************************************/

    /**
     * 存储巡店过程的网点ID，便于和网点列表进行比较
     */
    private const val PREF_TOUR_SHOP_ID = "tourShopID"
    private const val PREF_TOUR_SHOP_NAME = "tourShopName"

    private const val KEY_SHOP_ID = "keyShopID"
    private const val KEY_SHOP_NAME = "keyShopName"

    private val shopIdPref: SharedPreferences by lazy {
        appContext.sharedPref(PREF_TOUR_SHOP_ID)
    }

    private val shopNamePref: SharedPreferences by lazy {
        appContext.sharedPref(PREF_TOUR_SHOP_NAME)
    }

    private fun putShopID(shopId: String?) {
        shopIdPref.putKeyValue(KEY_SHOP_ID, shopId.notEmpty())
    }

    private fun getShopID(): String? {
        return shopIdPref.getString(KEY_SHOP_ID, "")
    }


    fun putShopName(shopName: String?) {
        shopNamePref.putKeyValue(KEY_SHOP_NAME, shopName.notEmpty())
    }

    fun getShopName(): String? {
        return shopNamePref.getString(KEY_SHOP_NAME, "")
    }

    fun clearPrefShopID() {
        shopIdPref.clearAll()
        shopNamePref.clearAll()
    }

    /**
     * 更新网点ID，便于和网点列表进行比较
     */
    fun saveShopIDPref(newShopID: String) {
        putShopID(newShopID)
        logger {
            "TouringDataBean save shopId: $newShopID"
        }
    }

    /**
     * 获取网点ID
     */
    fun getShopIDPref(): String? {
        val pref = getShopID()
        logger {
            "TouringDataBean get shopId: $pref"
        }
        return pref
    }

    /**
     * shopId比较，并更新缓存shopID
     */
    fun shopIdEqual(shopId: String?, preShopId: String?): Boolean {
        return (preShopId.noEmpty() && shopId == preShopId)
    }

    /**
     * shopId比较，是否继续巡店
     */
    fun shopIdEqual(shopId: String?): Boolean {
        return (getShopID().noEmpty() && shopId == getShopID())
    }
}


