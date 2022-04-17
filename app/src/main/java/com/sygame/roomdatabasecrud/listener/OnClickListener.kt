package com.sygame.roomdatabasecrud.listener

import com.sygame.roomdatabasecrud.room.User

interface OnClickListener {
    fun onItemClick(user: User)
    fun onItemDelete(user: User)
}