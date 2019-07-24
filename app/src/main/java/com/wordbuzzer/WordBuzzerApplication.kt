package com.wordbuzzer

import android.app.Activity
import android.app.Application
import com.wordbuzzer.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WordBuzzerApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var instance: WordBuzzerApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        instance = this
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingInjector
    }
}