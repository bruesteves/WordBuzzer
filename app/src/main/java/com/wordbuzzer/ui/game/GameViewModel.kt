package com.wordbuzzer.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wordbuzzer.domain.observer.SingleObserver
import com.wordbuzzer.domain.usecase.game.GetTranslationUseCase
import com.wordbuzzer.domain.usecase.playerselection.GetPlayersAvatarsUseCase
import com.wordbuzzer.model.game.Translation
import com.wordbuzzer.model.player.PlayerAvatars
import com.wordbuzzer.ui.utils.ViewModelResponse
import javax.inject.Inject

class GameViewModel @Inject constructor(val getTranslationUseCase: GetTranslationUseCase) : ViewModel() {

    val translationResponse: MutableLiveData<ViewModelResponse<Translation>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        getTranslationUseCase.dispose()
    }

    fun getTranslation() {
        getTranslationUseCase.execute(object : SingleObserver<Translation>() {
            override fun error(e: Throwable?) {
                translationResponse.value = ViewModelResponse.error(e)
            }

            override fun success(t: Translation?) {
                translationResponse.value = ViewModelResponse.success(t)
            }
        })
    }
}