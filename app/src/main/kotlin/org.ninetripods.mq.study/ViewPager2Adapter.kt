package org.ninetripods.mq.study

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val PAGES_NUM = 4

class ViewPager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = PAGES_NUM

    override fun createFragment(position: Int): Fragment = ViewPager2Fragment(position)

}