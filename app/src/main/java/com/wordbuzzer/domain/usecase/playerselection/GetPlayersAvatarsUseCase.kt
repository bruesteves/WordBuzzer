package com.wordbuzzer.domain.usecase.playerselection

import com.wordbuzzer.domain.thread.PostExecutionThread
import com.wordbuzzer.domain.usecase.SingleUseCase
import com.wordbuzzer.model.player.PlayerAvatars
import com.wordbuzzer.repository.PlayerRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPlayersAvatarsUseCase @Inject constructor(
    postExecutionThread: PostExecutionThread,
    val playerRepository: PlayerRepository): SingleUseCase<PlayerAvatars, Nothing?>(postExecutionThread){

    override fun buildUseCaseObservable(params: Nothing?): Single<PlayerAvatars> {
        return playerRepository.getPlayersAvatars()
    }
}