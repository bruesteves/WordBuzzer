package com.wordbuzzer.domain.usecase.game

import com.wordbuzzer.domain.thread.PostExecutionThread
import com.wordbuzzer.domain.usecase.SingleUseCase
import com.wordbuzzer.model.game.Translation
import com.wordbuzzer.model.player.PlayerAvatars
import com.wordbuzzer.repository.GameRepository
import com.wordbuzzer.repository.PlayerRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTranslationUseCase @Inject constructor(
    postExecutionThread: PostExecutionThread,
    val gameRepository: GameRepository): SingleUseCase<Translation, Nothing?>(postExecutionThread){

    override fun buildUseCaseObservable(params: Nothing?): Single<Translation> {
        return gameRepository.getTranslation()
    }
}