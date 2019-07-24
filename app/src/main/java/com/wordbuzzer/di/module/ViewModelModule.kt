package com.wordbuzzer.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wordbuzzer.di.viewmodel.ViewModelFactory
import com.wordbuzzer.di.viewmodel.ViewModelKey
import com.wordbuzzer.ui.game.GameViewModel
import com.wordbuzzer.ui.playerselection.activity.PlayerSelectionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PlayerSelectionViewModel::class)
    abstract fun bindPlayerSelectionViewModel(viewModel: PlayerSelectionViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(viewModel: GameViewModel) : ViewModel

}