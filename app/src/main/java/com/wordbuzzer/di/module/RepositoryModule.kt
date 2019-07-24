package com.wordbuzzer.di.module

import com.wordbuzzer.domain.thread.PostExecutionThread
import com.wordbuzzer.domain.thread.UiThread
import com.wordbuzzer.repository.GameRepository
import com.wordbuzzer.repository.GameRepositoryImpl
import com.wordbuzzer.repository.PlayerRepository
import com.wordbuzzer.repository.PlayerRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    fun bindPostExecutionThread() : PostExecutionThread {
        return UiThread()
    }

    @Singleton
    @Provides
    fun providePlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideGameRepository(): GameRepository {
        return GameRepositoryImpl()
    }
}