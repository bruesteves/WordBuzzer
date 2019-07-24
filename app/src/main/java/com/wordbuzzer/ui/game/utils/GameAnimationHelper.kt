package com.wordbuzzer.ui.game.utils

import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kotlin.random.Random

object GameAnimationHelper {

    fun animateViewWithRandomYOrigin(target: View, view: View, listener: AnimatorListenerAdapter): AnimatorSet {
        val fade = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f)

        var move: ObjectAnimator? = null
        if (Random.nextInt(2000) % 2 == 0) {
            target.y = -view.height.toFloat()
            move = ObjectAnimator.ofFloat(
                target, "translationY", -view.height.toFloat(), view.height.toFloat()
            )
        } else {
            target.y = view.height.toFloat()
            move = ObjectAnimator.ofFloat(
                target, "translationY", view.height.toFloat(), -view.height.toFloat()
            )
        }

        val animatorSet = AnimatorSet()
        animatorSet.play(move).after(fade)

        animatorSet.duration = 2500

        animatorSet.startDelay = 0

        animatorSet.addListener(listener)

        animatorSet.start()

        return animatorSet
    }

}