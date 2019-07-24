package com.wordbuzzer.di.module

import com.wordbuzzer.ui.onboarding.fragments.OnboardingFirstFragment
import com.wordbuzzer.ui.onboarding.fragments.OnboardingSecondFragment
import com.wordbuzzer.ui.onboarding.fragments.OnboardingThirdFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindOnboardingFirstFragment(): OnboardingFirstFragment

    @ContributesAndroidInjector
    abstract fun bindOnboardingSecondFragment(): OnboardingSecondFragment

    @ContributesAndroidInjector
    abstract fun bindOnboardingThirdFragment(): OnboardingThirdFragment
}