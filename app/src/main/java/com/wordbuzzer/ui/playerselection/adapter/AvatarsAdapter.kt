package com.wordbuzzer.ui.playerselection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wordbuzzer.R
import kotlinx.android.synthetic.main.view_player_avatar.view.*

class AvatarsAdapter(val avatars : List<Int>, val context: Context, val onClickListener: View.OnClickListener) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return avatars.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_player_avatar, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar = avatars.get(position)
        Glide.with(context).load(avatar).into(holder.image)

        holder.container.tag = avatar

        holder.container.setOnClickListener {
            onClickListener.onClick(it)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val image = view.player_avatar_iv
    val container = view.player_avatar_container
}