package com.sygame.roomdatabasecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sygame.roomdatabasecrud.room.Constant
import com.sygame.roomdatabasecrud.room.User
import com.sygame.roomdatabasecrud.room.UserDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    lateinit var judul: EditText
    lateinit var deskripsi: EditText
    lateinit var speichern: Button
    lateinit var aktualisieren: Button

    val db by lazy {UserDataBase(this)}

    private var userId: Int = 0
    private var userType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        judul = findViewById(R.id.judul)
        deskripsi = findViewById(R.id.deskripsi)
        speichern = findViewById(R.id.speichern)
        aktualisieren = findViewById(R.id.aktualisieren)

        setupView()
        setUpListenerSave()

        //userId = intent.getIntExtra("intent_id",0)
        //Toast.makeText(this,userId.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //utk membuat back tampilan
        userType = intent.getIntExtra("intent_type",0)
        when(userType){
            Constant.TYPE_CREATE -> {
                aktualisieren.visibility = View.GONE
                supportActionBar!!.title = "Create New Note"
            }
            Constant.TYPE_READ -> {
                speichern.visibility = View.GONE
                aktualisieren.visibility = View.GONE
                supportActionBar!!.title = "Read Note"
                getData()
            }
            Constant.TYPE_UPDATE -> {
                speichern.visibility = View.GONE
                supportActionBar!!.title = "Update Note"
                getData()
            }
        }
    }

    private fun setUpListenerSave(){
        speichern.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addData(
                    User(0,judul.text.toString(),
                        deskripsi.text.toString())
                )
                finish()
            }
        }
        aktualisieren.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().updateData(
                    User(userId,judul.text.toString(),
                        deskripsi.text.toString())
                )
                finish()
            }
        }
    }

    private fun getData(){
        userId = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val daten = db.userDao().getData(userId)[0]
            judul.setText(daten.judul)
            deskripsi.setText(daten.deskripsi)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}