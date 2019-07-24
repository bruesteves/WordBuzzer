package com.wordbuzzer.di.component

import android.app.Application
import com.wordbuzzer.WordBuzzerApplication
import com.wordbuzzer.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class,
    ViewModelModule::class,
    RepositoryModule::class])

@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WordBuzzerApplication)
}