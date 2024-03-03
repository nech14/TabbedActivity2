package com.example.tabbedactivity.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tabbedactivity.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment.
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getStringArray(R.array.cities)[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}