package com.wordbuzzer.repository

import com.wordbuzzer.R
import com.wordbuzzer.model.player.PlayerAvatars
import io.reactivex.Observable
import io.reactivex.Single

class PlayerRepositoryImpl() : PlayerRepository {

    override fun getPlayersAvatars(): Single<PlayerAvatars> {
        return Single.just(
            PlayerAvatars(
                listOf(
                    R.drawable.ic_boy,
                    R.drawable.ic_boy_1,
                    R.drawable.ic_girl,
                    R.drawable.ic_girl_1,
                    R.drawable.ic_man,
                    R.drawable.ic_man_1,
                    R.drawable.ic_man_2,
                    R.drawable.ic_man_3,
                    R.drawable.ic_man_4
                )
            )
        )
    }
}