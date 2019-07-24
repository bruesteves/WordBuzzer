package com.wordbuzzer.ui.game

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.wordbuzzer.model.game.PossibleTranslation
import com.wordbuzzer.model.game.Translation
import com.wordbuzzer.ui.base.BaseActivity
import com.wordbuzzer.ui.kotterknife.bindView
import com.wordbuzzer.ui.playerselection.activity.EXTRA_PLAYER_ONE_AVATAR
import com.wordbuzzer.ui.playerselection.activity.EXTRA_PLAYER_TWO_AVATAR
import com.wordbuzzer.ui.utils.ViewModelResponse
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView
import com.wordbuzzer.R
import com.wordbuzzer.model.player.Player
import com.wordbuzzer.ui.game.utils.GameAnimationHelper
import kotlin.random.Random

class GameActivity : BaseActivity() {

    val ivAvatarPlayerOne: ImageView by bindView(R.id.game_avatar_iv_player_one)
    val ivAvatarPlayerTwo: ImageView by bindView(R.id.game_avatar_iv_player_two)
    val txtWordToTranslate: TextView by bindView(R.id.game_txt_word_to_translate)
    val txtWinner: TextView by bindView(R.id.game_winner)
    val txtWordPossibleTranslate: TextView by bindView(R.id.game_avatar_word_possible_translation)
    val buttonBuzzPlayerOne: Button by bindView(R.id.game_buzz_player_one)
    val buttonBuzzPlayerTwo: Button by bindView(R.id.game_buzz_player_two)
    val lottieAnim: LottieAnimationView by bindView(R.id.game_lottie)
    val endMask: View by bindView(R.id.game_end_mask)
    val canvas: View by bindView(R.id.game_canvas)

    enum class PlayerType {
        PLAYERONE, PLAYERTWO
    }

    private var roundCount = 0
    private val roundMax = 5

    private var scoutPlayerOne = 0
    private var scoutPlayerTwo = 0

    var rightAnswer: String? = null
    var currentAnimatorSet: AnimatorSet? = null

    private val gameViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        getDataFromIntent()
        registerObserver()
        registerListeners()
        gameViewModel.getTranslation()
    }

    private fun registerObserver() {
        gameViewModel.translationResponse.observe(this, translationObserver)
    }

    private fun registerListeners() {
        buttonBuzzPlayerOne.setOnClickListener {
            checkAnswer(PlayerType.PLAYERONE)
        }

        buttonBuzzPlayerTwo.setOnClickListener {
            checkAnswer(PlayerType.PLAYERTWO)
        }

        setLottieListener()
    }

    private val translationObserver = Observer<ViewModelResponse<Translation>> {
        when (it?.status) {
            ViewModelResponse.Status.SUCCESS -> showTranslation(it.data)
            ViewModelResponse.Status.ERROR -> showError(it.messageError)
        }
    }

    private fun showError(messageError: String?) {
        if (messageError == null) return
        Toast.makeText(this, messageError, Toast.LENGTH_LONG).show()
    }

    private fun getDataFromIntent() {
        if (intent.hasExtra(EXTRA_PLAYER_ONE_AVATAR))
            Glide.with(this).load(intent.getIntExtra(EXTRA_PLAYER_ONE_AVATAR, Int.MIN_VALUE)).into(ivAvatarPlayerOne)

        if (intent.hasExtra(EXTRA_PLAYER_TWO_AVATAR))
            Glide.with(this).load(intent.getIntExtra(EXTRA_PLAYER_TWO_AVATAR, Int.MIN_VALUE)).into(ivAvatarPlayerTwo)
    }

    private fun showTranslation(translation: Translation?) {
        if (translation == null) return

        txtWordToTranslate.text = translation.fromValue
        rightAnswer = translation.toValue

        animatePossibleTranslations(translation.possibleTranslations)
    }

    private fun animatePossibleTranslations(possibleTranslations: ArrayList<PossibleTranslation?>?) {
        if (possibleTranslations == null) return

        val listener = object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                val index = Random.nextInt(possibleTranslations.size)
                txtWordPossibleTranslate.text = possibleTranslations.get(index)?.toValue
            }
            override fun onAnimationEnd(animation: Animator?) {
                gameViewModel.getTranslation()
            }
        }

        currentAnimatorSet = GameAnimationHelper.animateViewWithRandomYOrigin(txtWordPossibleTranslate, canvas, listener)
    }

    private fun checkAnswer(player: PlayerType) {
        var success = false
        if (txtWordPossibleTranslate.text == rightAnswer) {
            showRoundSucess()
            success = true
        } else {
            showRoundError()
            scoutPlayerTwo = scoutPlayerTwo + 1
        }
        if (success) {
            when (player) {
                PlayerType.PLAYERONE -> {
                    scoutPlayerOne = scoutPlayerOne + 1
                }

                PlayerType.PLAYERTWO -> {
                    scoutPlayerTwo = scoutPlayerTwo + 1
                }
            }
        }
    }

    private fun pauseAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            currentAnimatorSet?.pause()
        }
    }

    private fun startAnim() {
        currentAnimatorSet?.start()
    }

    private fun showRoundSucess() {
        pauseAnim()

        lottieAnim.visibility = View.VISIBLE
        lottieAnim.setAnimation("check.json")

        lottieAnim.playAnimation()
    }

    private fun showRoundError() {
        lottieAnim.visibility = View.VISIBLE
        lottieAnim.setAnimation("error.json")

        lottieAnim.playAnimation()
    }

    private fun setLottieListener() {
        lottieAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                lottieAnim.visibility = View.GONE
                startAnim()

                roundCount = roundCount + 1
                checkEndGame()
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}

        })
    }

    private fun checkEndGame() {
        if (roundCount == roundMax) {
            txtWordToTranslate.visibility = View.GONE
            pauseAnim()

            txtWinner.text = getString(
                R.string.game_winner_sentence,
                if (scoutPlayerOne > scoutPlayerTwo) getString(R.string.game_player_one)
                else getString(R.string.game_player_two)
            )
            txtWinner.visibility = View.VISIBLE
            endMask.visibility = View.VISIBLE

            lottieAnim.visibility = View.VISIBLE
            lottieAnim.setAnimation("fireworks.json")
            lottieAnim.playAnimation()
        }
    }
}