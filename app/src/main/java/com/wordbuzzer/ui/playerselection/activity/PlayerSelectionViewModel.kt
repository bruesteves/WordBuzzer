package com.wordbuzzer.ui.playerselection.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wordbuzzer.domain.observer.SingleObserver
import com.wordbuzzer.domain.usecase.playerselection.GetPlayersAvatarsUseCase
import com.wordbuzzer.model.player.PlayerAvatars
import com.wordbuzzer.ui.utils.ViewModelResponse
import javax.inject.Inject

class PlayerSelectionViewModel @Inject constructor(val playersAvatarsUseCase: GetPlayersAvatarsUseCase) : ViewModel() {

    val avatarsPlayerOneResponse: MutableLiveData<ViewModelResponse<PlayerAvatars>> = MutableLiveData()
    val avatarsPlayerTwoResponse: MutableLiveData<ViewModelResponse<PlayerAvatars>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        playersAvatarsUseCase.dispose()
    }

    fun getPlayerOneAvatars() {
        playersAvatarsUseCase.execute(object : SingleObserver<PlayerAvatars>() {
            override fun error(e: Throwable?) {
                avatarsPlayerOneResponse.value = ViewModelResponse.error(e)
            }

            override fun success(t: PlayerAvatars?) {
                avatarsPlayerOneResponse.value = ViewModelResponse.success(t)
            }
        })
    }

    fun getPlayerTwoAvatars() {
        playersAvatarsUseCase.execute(object : SingleObserver<PlayerAvatars>() {
            override fun error(e: Throwable?) {
                avatarsPlayerTwoResponse.value = ViewModelResponse.error(e)
            }

            override fun success(t: PlayerAvatars?) {
                avatarsPlayerTwoResponse.value = ViewModelResponse.success(t)
            }
        })
    }
}