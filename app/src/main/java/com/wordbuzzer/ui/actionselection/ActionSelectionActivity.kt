package com.wordbuzzer.ui.actionselection

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wordbuzzer.R
import com.wordbuzzer.ui.base.BaseActivity
import com.wordbuzzer.ui.kotterknife.bindView
import com.wordbuzzer.ui.onboarding.activity.OnboardingActivity
import com.wordbuzzer.ui.playerselection.activity.PlayerSelectionActivity

class ActionSelectionActivity : BaseActivity() {

    val btnPlayerSelection: Button by bindView(R.id.select_action_btn_player_selection)
    val btnOnboarding: Button by bindView(R.id.select_action_btn_onboarding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_selection)
        registerListeners()
    }

    private fun registerListeners(){
        btnOnboarding.setOnClickListener {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }

        btnPlayerSelection.setOnClickListener {
            startActivity(Intent(this, PlayerSelectionActivity::class.java))
        }
    }
}
