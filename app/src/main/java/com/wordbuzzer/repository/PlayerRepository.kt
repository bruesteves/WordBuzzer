package com.wordbuzzer.repository
import com.wordbuzzer.model.player.PlayerAvatars
import io.reactivex.Single

interface PlayerRepository {

    fun getPlayersAvatars(): Single<PlayerAvatars>
}