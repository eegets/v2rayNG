package com.forest.bss.sdk.base.adapter.expand

import android.content.Context
import android.view.InflateException
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.annotation.LayoutRes
import com.forest.bss.sdk.ext.nonNull

/**
 * Created by wangkai on 2021/02/19 14:05

 * Desc 封装的BaseExpandAdapter类，抽离出公共代码
 */

abstract class BaseExpandAdapter<GroupBean, ChildBean>(val context: Context) : BaseExpandableListAdapter() {

    var listData: List<BaseExpandEntity<GroupBean, ChildBean>>? = mutableListOf()

    override fun getGroup(groupPosition: Int): BaseExpandEntity<GroupBean, ChildBean>? {
        return listData?.get(groupPosition)
    }

    /**
     * 子列表是否可点击
     */
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        if (groupLayout() == 0) {
            throw InflateException("please return xml Layout")
        }
        val holder = ViewExpandHolder.getInstance(context, convertView, groupLayout())
        convertGroupView(holder, listData?.get(groupPosition)?.groupBean, groupPosition, isExpanded)
        return holder?.getConvertView()
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listData?.get(groupPosition)?.childList?.size.nonNull(0)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): ChildBean? {
        return listData?.get(groupPosition)?.childList?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        if (childLayout() == 0) {
            throw InflateException("please return xml Layout")
        }
        val holder = ViewExpandHolder.getInstance(context, convertView, childLayout())
        convertChildView(holder, listData?.get(groupPosition)?.childList?.get(childPosition), groupPosition, childPosition, isLastChild)
        return holder?.getConvertView()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return listData?.size.nonNull(0)
    }

    @LayoutRes
    abstract fun groupLayout(): Int

    @LayoutRes
    abstract fun childLayout(): Int

    abstract fun convertChildView(holder: ViewExpandHolder?, childBean: ChildBean?, groupPosition: Int, childPosition: Int, isLastChild: Boolean)

    abstract fun convertGroupView(holder: ViewExpandHolder?, groupBean: GroupBean?, groupPosition: Int, isExpanded: Boolean)


}