package com.wordbuzzer.repository
import com.wordbuzzer.model.game.Translation
import com.wordbuzzer.model.player.PlayerAvatars
import io.reactivex.Single

interface GameRepository {

    fun getTranslation(): Single<Translation>
}