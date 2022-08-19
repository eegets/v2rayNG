package com.forest.bss.sdk.base.adapter.expand

/**
 * Created by wangkai on 2021/02/19 14:20

 * Desc TODO
 */
data class BaseExpandEntity<GroupBean, ChildBean>(val groupBean: GroupBean, val childList: List<ChildBean>?)