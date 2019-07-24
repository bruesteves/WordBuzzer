package com.wordbuzzer.ui.playerselection.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wordbuzzer.R
import com.wordbuzzer.model.player.PlayerAvatars
import com.wordbuzzer.ui.base.BaseActivity
import com.wordbuzzer.ui.game.GameActivity
import com.wordbuzzer.ui.kotterknife.bindView
import com.wordbuzzer.ui.playerselection.adapter.AvatarsAdapter
import com.wordbuzzer.ui.utils.ViewModelResponse

const val EXTRA_PLAYER_ONE_AVATAR = "EXTRA_PLAYER_ONE_AVATAR"
const val EXTRA_PLAYER_TWO_AVATAR = "EXTRA_PLAYER_TWO_AVATAR"
class PlayerSelectionActivity : BaseActivity() {

    val recyclerPlayerOne: RecyclerView by bindView(R.id.player_selection_recyclerview_player_one)
    val recyclerPlayerTwo: RecyclerView by bindView(R.id.player_selection_recyclerview_player_two)
    val maskPlayerOne: TextView by bindView(R.id.player_selection_mask_player_one)
    val maskPlayerTwo: TextView by bindView(R.id.player_selection_mask_player_two)

    private var playerOneAvatar : Int? = null
    private var playerTwoAvatar : Int? = null

    private val playerSelectionViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PlayerSelectionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_selection)

        registerObservers()
        playerSelectionViewModel.getPlayerOneAvatars()
        playerSelectionViewModel.getPlayerTwoAvatars()
    }

    private fun registerObservers() {
        playerSelectionViewModel.avatarsPlayerOneResponse.observe(this, avatarsPlayerOneObserver)
        playerSelectionViewModel.avatarsPlayerTwoResponse.observe(this, avatarsPlayerTwoObserver)
    }

    private val avatarsPlayerOneObserver = Observer<ViewModelResponse<PlayerAvatars>> {
        when (it?.status) {
            ViewModelResponse.Status.SUCCESS -> createAvatarsRecycler(it.data, recyclerPlayerOne, playerOneOnClickListener)
            ViewModelResponse.Status.ERROR -> showError(it.messageError)
        }
    }

    private val avatarsPlayerTwoObserver = Observer<ViewModelResponse<PlayerAvatars>> {
        when (it?.status) {
            ViewModelResponse.Status.SUCCESS -> createAvatarsRecycler(it.data, recyclerPlayerTwo, playerTwoOnClickListener)
            ViewModelResponse.Status.ERROR -> showError(it.messageError)
        }
    }

    private val playerOneOnClickListener = View.OnClickListener {
        it.background = ColorDrawable(ContextCompat.getColor(this, R.color.alphaBlack))
        maskPlayerOne.visibility = View.VISIBLE
        playerOneAvatar = it.tag as Int
        checkToGoToGame()
    }

    private val playerTwoOnClickListener = View.OnClickListener {
        it.background = ColorDrawable(ContextCompat.getColor(this, R.color.alphaBlack))
        maskPlayerTwo.visibility = View.VISIBLE
        playerTwoAvatar = it.tag as Int
        checkToGoToGame()
    }

    private fun createAvatarsRecycler(avatars: PlayerAvatars?, recyclerView: RecyclerView, onClickListener: View.OnClickListener){
        if(avatars == null) return

        val manager = GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = AvatarsAdapter(avatars.imageList, this, onClickListener)
    }

    private fun showError(messageError : String?){
        if(messageError == null) return
        Toast.makeText(this, messageError, Toast.LENGTH_LONG).show()
    }

    private fun checkToGoToGame(){
        if(maskPlayerTwo.visibility == View.VISIBLE && maskPlayerOne.visibility == View.VISIBLE) {
            val intent = Intent(this, GameActivity::class.java)
            playerOneAvatar?.let {
                intent.putExtra(EXTRA_PLAYER_ONE_AVATAR, it)
            }

            playerTwoAvatar?.let {
                intent.putExtra(EXTRA_PLAYER_TWO_AVATAR, it)
            }
            startActivity(intent)
            finish()
        }
    }
}
