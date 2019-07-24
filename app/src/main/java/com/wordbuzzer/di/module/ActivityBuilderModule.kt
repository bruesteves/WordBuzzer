package com.wordbuzzer.di.module

import com.wordbuzzer.ui.actionselection.ActionSelectionActivity
import com.wordbuzzer.ui.game.GameActivity
import com.wordbuzzer.ui.onboarding.activity.OnboardingActivity
import com.wordbuzzer.ui.playerselection.activity.PlayerSelectionActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindActionSelectionActivity(): ActionSelectionActivity

    @ContributesAndroidInjector
    abstract fun bindOnboardingActivity(): OnboardingActivity

    @ContributesAndroidInjector
    abstract fun bindPlayerSelectionActivity(): PlayerSelectionActivity

    @ContributesAndroidInjector
    abstract fun bindGameActivity(): GameActivity
}