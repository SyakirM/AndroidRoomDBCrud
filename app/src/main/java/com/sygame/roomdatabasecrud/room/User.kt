package com.sygame.roomdatabasecrud.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var judul: String,
    var deskripsi: String
)
