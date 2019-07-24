package com.wordbuzzer.ui.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wordbuzzer.ui.onboarding.fragments.OnboardingFirstFragment
import com.wordbuzzer.ui.onboarding.fragments.OnboardingSecondFragment
import com.wordbuzzer.ui.onboarding.fragments.OnboardingThirdFragment

class OnboardingPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    init {
        fragmentList.addAll(
            arrayListOf(
                OnboardingFirstFragment(),
                OnboardingSecondFragment(),
                OnboardingThirdFragment()
            )
        )
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}