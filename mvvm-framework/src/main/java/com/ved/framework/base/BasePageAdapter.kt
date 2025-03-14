package com.ved.framework.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BasePageAdapter : FragmentStateAdapter {
    private val mFragments = mutableListOf<Fragment>()

    fun addFragment(fragmentList: MutableList<Fragment>){
        mFragments.addAll(fragmentList)
    }

    constructor(fragment: Fragment) : super(fragment)

    constructor(fragmentManager: FragmentManager,lifecycle: Lifecycle) : super(fragmentManager, lifecycle)

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    override fun getItemCount() = mFragments.size

    override fun createFragment(position: Int) = mFragments[position]
}