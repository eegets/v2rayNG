package com.forest.bss.sdk.base.frag

import androidx.fragment.app.Fragment

/**
 * Created by wangkai on 2021/04/15 17:34

 * Desc TODO
 */

public abstract class BaseVisibleFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        fragmentVisible()
    }

    override fun onPause() {
        super.onPause()
        fragmentUnVisible()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            fragmentVisible()
        } else {
            fragmentUnVisible()
        }
    }

    /**
     * Fragment显示，包括tab切换以及界面恢复到可见
     */
    open fun fragmentVisible() {}

    /**
     * Fragment不显示，包括tab切换以及界面不可见
     */
    open fun fragmentUnVisible() {}
}