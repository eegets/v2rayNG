package com.forest.bss.sdk.base.adapter.expand

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import com.forest.bss.sdk.ext.asType


/**
 * Created by wangkai on 2021/02/19 14:56

 * Desc 适配器通用的ViewExpandHolder，适用于BaseExpandAdapter
 */

class ViewExpandHolder(private val convertView: View?) {

    /**
     *  SparseArray效率比HashMap更高
     */
    private var views : SparseArray<View?>? = SparseArray()

    init {
        convertView?.tag = this
    }
    companion object {
        @JvmStatic
        fun getInstance(context: Context?, convertView: View?, layout: Int): ViewExpandHolder? {
            var convertView = convertView
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layout, null)
                return ViewExpandHolder(convertView)
            }
            return convertView.tag.asType<ViewExpandHolder>() // 重用convertView的逻辑
        }
    }

    fun <T : View?> findViewById(id: Int): T? {
        var view = views!![id]
        if (view == null) {
            view = convertView?.findViewById(id)
            views?.append(id, view)
        }
        return view as T?
    }

    fun getConvertView(): View? {
        return convertView
    }

}