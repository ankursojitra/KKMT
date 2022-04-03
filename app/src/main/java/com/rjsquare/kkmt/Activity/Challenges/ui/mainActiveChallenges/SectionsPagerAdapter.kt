package com.rjsquare.kkmt.Activity.Challenges.ui.mainActiveChallenges

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rjsquare.kkmt.R


class SectionsPagerAdapter(
    private val mContext: Context,
    fm: FragmentManager?
) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (position == 0) {
            return ChallengActive.newInstance()
        } else if (position == 1) {
            return ComplateChallenges.newInstance()
        } else {
            return ChallengActive.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources
            .getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }

    companion object {
        @StringRes
        private val TAB_TITLES =
            intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

}