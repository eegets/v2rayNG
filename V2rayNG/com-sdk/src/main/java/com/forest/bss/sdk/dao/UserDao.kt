package com.forest.bss.sdk.dao

import android.content.SharedPreferences
import com.forest.bss.sdk.ext.*
import com.forest.net.ext.clearAll

/**
 * Created by wangkai on 2021/02/04 15:54

 * Desc 用户信息存储
 */

object UserDao {
    private const val PREF_USER_DAO = "UserDaoPref"

    /**
     * 部门
     */
    private const val DEPT_NAME = "deptName"

    /**
     * 性别
     */
    private const val GENDER = "gender"

    /**
     * 用户ID，非token
     */
    private const val ID = "id"

    /**
     * 直属领导名称
     */
    private const val LEADER_NAME = "leaderName"

    /**
     * 昵称
     */
    private const val NAME = "name"

    /**
     * 手机号
     */
    private const val PHONE = "phone"

    /**
     * 职位
     */
    private const val POSITION = "position"

    /**
     * 用户类型，乳品/饮品
     */
    private const val TYPE = "TYPE"

    /**
     * 更新时间
     */
    private const val CREATED_AT = "createdAt"


    /**
     * 经销商编号
     */
    private const val CLIENT_CODE = "clientCode"


    /**
     * 是否设置过密码
     */
    private const val PWD_FLAG = "pwdHash"

    /**
     * 是否为基层业务人员
     */
    private const val IS_GRASSROOTS = "isGrassroots"

    /**
     * 经销商名称
     */
    private const val CLIENT_NAME = "clientName"

    /**
     * token
     */
    private const val TOKEN = "token"

    /**
     * 搜索历史
     */
    private const val SEARCH_HISTORY = "searchHistory"

    /**
     * 手机型号
     */
    private const val HEADER_TYPE = "p_type"

    /**
     * 手机系统
     */
    private const val HEADER_SYSTEM = "p_system"

    /**
     * 手机系统版本号
     */
    private const val HEADER_S_VERSION = "p_s_version"



    /**
     * 设备唯一标识
     */
    private const val HEADER_P_U_MARK = "p_u_mark"

    /**
     * 版本号
     */
    private const val HEADER_A_VERSION = "a_version"

    /**
     * 主数据Id
     * 任务需要
     */
    private const val USER_MASTER_ID = "userMasterId"

    /**
     * 首页配置信息Json
     */
    private const val MAIN_CONFIG_INFO = "mainConfigInfo"

    /**
     * 手机号，手机号，手机号，这就是代表手机号
     */
    private const val STATUS_NEWAPP = "newApp"

    private val userDao: SharedPreferences by lazy {
        appContext.sharedPref(PREF_USER_DAO)
    }

    /**
     * 部门
     */
    fun putDeptName(deptName: String?) {
        userDao.putKeyValue(DEPT_NAME, deptName ?: "")
    }

    fun getDeptName(): String? {
        return userDao.getString(DEPT_NAME, "")
    }

    /**
     * 职位
     */
    fun putPosition(deptName: String?) {
        userDao.putKeyValue(POSITION, deptName ?: "")
    }

    fun getPosition(): String? {
        return userDao.getString(POSITION, "")
    }

    /**
     * 性别 1：男性 2：女性
     */
    fun putGender(gender: Int) {
        userDao.putKeyValue(GENDER, gender)
    }

    fun getGender(): Int {
        return userDao.getInt(GENDER, 1)
    }

    /**
     * 用户ID，非token
     */
    fun putId(id: String) {

        com.forest.net.ext.defaultSharedPref?.putKeyValue(ID, id)
        userDao.putKeyValue(ID, id)
    }

    fun getId(): String? {
        return userDao.getString(ID, "")
    }

    /**
     * 直属领导名称
     */
    fun putLeaderName(leaderName: String?) {
        userDao.putKeyValue(LEADER_NAME, leaderName ?: "")
    }

    fun getLeaderName(): String? {
        return userDao.getString(LEADER_NAME, "")
    }

    /**
     * 昵称
     */
    fun putName(name: String?) {
        userDao.putKeyValue(NAME, name ?: "")
    }

    fun getName(): String? {
        return userDao.getString(NAME, "")
    }

    /**
     * 手机号
     */
    fun putPhone(phone: String?) {
        userDao.putKeyValue(PHONE, phone ?: "")
    }

    fun getPhone(): String? {
        return userDao.getString(PHONE, "")
    }

    /**
     * 用户类型，乳品/饮品  1：乳品，2：饮品
     */
    fun putType(type: Int) {
        userDao.putKeyValue(TYPE, type)
    }

    fun getType(): Int {
        return userDao.getInt(TYPE, 1)
    }

    /**
     * 更新时间
     */
    fun putCreatedAt(updateAt: String?) {
        userDao.putKeyValue(CREATED_AT, updateAt ?: "")
    }

    fun getCreatedAt(): String? {
        return userDao.getString(CREATED_AT, "")
    }

    /**
     * 经销商编号
     */
    fun putClientCode(clientCode: String?) {
        userDao.putKeyValue(CLIENT_CODE, clientCode.notEmpty())
    }

    fun getClientCode(): String? = userDao.getString(CLIENT_CODE, null)

    /**
     * 经销商名称
     */
    fun putClientName(clientName: String?) {
        userDao.putKeyValue(CLIENT_NAME, clientName.notEmpty())
    }

    fun getClientName(): String? = userDao.getString(CLIENT_NAME, "")

    /**
     * 是否设置过密码
     */
    fun putPwdFlag(pwdFlag: String?) {
        userDao.putKeyValue(PWD_FLAG, pwdFlag.notEmpty())
    }

    fun getPwdFlag(): String? = userDao.getString(PWD_FLAG, "0")

    /**
     * 是否是基层业务员
     */
    fun putIsGrassroots(isGrassroots: Boolean) = userDao.putKeyValue(IS_GRASSROOTS, isGrassroots)
    fun getIsGrassroots(): Boolean = userDao.getBoolean(IS_GRASSROOTS, true)

    /**
     * Token
     */
    fun putToken(token: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(TOKEN, "$token" ?: "")
        userDao.putKeyValue(TOKEN, "$token" ?: "")
    }

    fun getToken(): String? {
        return userDao.getString(TOKEN, "")
    }

    /**
     * 搜索历史
     */
    fun putHistory(shopString: String?) {
        userDao.putKeyValue(SEARCH_HISTORY, shopString ?: "")
    }

    fun getHistory(): String? {
        return userDao.getString(SEARCH_HISTORY, "")
    }

    /**
     * 手机型号
     */
    fun putHeaderType(headerType: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(HEADER_TYPE, headerType ?: "")
        userDao.putKeyValue(HEADER_TYPE, headerType ?: "")
    }

    fun getHeaderType(): String? {
        return userDao.getString(HEADER_TYPE, "")
    }

    /**
     * 手机系统
     */
    fun putHeaderSystem(headerSystem: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(HEADER_SYSTEM, headerSystem ?: "")
        userDao.putKeyValue(HEADER_SYSTEM, headerSystem ?: "")
    }

    fun getHeaderSystem(): String? {
        return userDao.getString(HEADER_SYSTEM, "")
    }

    /**
     * 手机系统版本号
     */
    fun putHeaderPhoneSystem(headerPhoneSystem: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(HEADER_S_VERSION, headerPhoneSystem ?: "")
        userDao.putKeyValue(HEADER_S_VERSION, headerPhoneSystem ?: "")
    }

    fun getHeaderPhoneSystem(): String? {
        return userDao.getString(HEADER_S_VERSION, "")
    }

    /**
     * 设备唯一标识
     */
    fun putHeaderUMark(headerUMark: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(HEADER_P_U_MARK, headerUMark ?: "")
        userDao.putKeyValue(HEADER_P_U_MARK, headerUMark ?: "")
    }

    fun getHeaderUMark(): String? {
        return userDao.getString(HEADER_P_U_MARK, "")
    }

    /**
     * 版本号
     */
    fun putHeaderVersion(headerVersion: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(HEADER_A_VERSION, headerVersion ?: "")
        userDao.putKeyValue(HEADER_A_VERSION, headerVersion ?: "")
    }

    fun getHeaderVersion(): String? {
        return userDao.getString(HEADER_A_VERSION, "")
    }

    /**
     *
     * 主数据Id
     */
    fun putUserMasterId(userMasterId: String?) {
        userDao.putKeyValue(USER_MASTER_ID, userMasterId ?: "")
    }
    fun getUserMasterId(): String? {
        return userDao.getString(USER_MASTER_ID, "")
    }

     /**
      * 配置信息
     */
    fun putMainConfigInfo(mainConfigInfo: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(MAIN_CONFIG_INFO, mainConfigInfo ?: "")
        userDao.putKeyValue(MAIN_CONFIG_INFO, mainConfigInfo ?: "")
    }

    fun getMainConfigInfo(): String? {
        return userDao.getString(MAIN_CONFIG_INFO, "")
    }

    /**
     * 手机号，手机号，手机号，这就是代表手机号
     */
    fun putNewApp(newApp: String?) {
        com.forest.net.ext.defaultSharedPref?.putKeyValue(STATUS_NEWAPP, newApp ?: "")
    }

    /**
     * 清空用户信息
     */
    fun clearAll() {
        userDao.clearAll()
        putToken("")
        putHeaderUMark("")
    }
}