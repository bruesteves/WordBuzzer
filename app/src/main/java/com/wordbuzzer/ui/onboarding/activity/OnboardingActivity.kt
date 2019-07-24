package com.wordbuzzer.ui.onboarding.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.wordbuzzer.R
import com.wordbuzzer.ui.base.BaseActivity
import com.wordbuzzer.ui.kotterknife.bindView
import com.wordbuzzer.ui.onboarding.adapter.OnboardingPagerAdapter
import com.wordbuzzer.ui.playerselection.activity.PlayerSelectionActivity
import me.relex.circleindicator.CircleIndicator
import org.jetbrains.anko.textColor

class OnboardingActivity() : BaseActivity() {

    val viewPager: ViewPager by bindView(R.id.onboarding_viewpager)
    val button: Button by bindView(R.id.onboarding_button)
    val indicator : CircleIndicator by bindView(R.id.onboarding_vp_indicator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        registerListeners()

        viewPager.adapter = OnboardingPagerAdapter(this.supportFragmentManager)
        viewPager.addOnPageChangeListener(onPageListener)
        indicator.setViewPager(viewPager)
    }

    private val onPageListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            if (position == (viewPager.adapter?.count ?: 0) - 1) {
                button.apply {
                    background = ContextCompat.getDrawable(
                        this@OnboardingActivity,
                        R.drawable.colored_button
                    )
                    textColor = ContextCompat.getColor(
                        this@OnboardingActivity,
                        android.R.color.white
                    )
                    text = getString(R.string.onboarding_play_btn)
                }
            } else {
                button.apply {
                    background = ContextCompat.getDrawable(
                        this@OnboardingActivity,
                        R.drawable.inverted_button
                    )
                    textColor =
                        ContextCompat.getColor(this@OnboardingActivity, R.color.colorPrimary)
                    text = getString(R.string.onboarding_next_btn)
                }
            }
        }
    }

    private fun registerListeners() {
        button.setOnClickListener {
            if (viewPager.adapter == null) return@setOnClickListener

            val currentItem = viewPager.currentItem
            val count = viewPager.adapter!!.count

            if (currentItem < count - 1) {
                viewPager.currentItem = currentItem + 1
            } else if (currentItem == count - 1) {
                startPlayerSelection()
                this.finish()
            }
        }
    }

    private fun startPlayerSelection() {
        startActivity(Intent(this, PlayerSelectionActivity::class.java))
    }
}
