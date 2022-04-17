package com.sygame.roomdatabasecrud

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sygame.roomdatabasecrud.room.Constant
import com.sygame.roomdatabasecrud.room.User
import com.sygame.roomdatabasecrud.room.UserDataBase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var addNew: Button
    lateinit var rvData: RecyclerView

    val db by lazy { UserDataBase(this) }

    lateinit var mainAdapter:  MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Note"

        addNew = findViewById(R.id.addNew)
        rvData = findViewById(R.id.rvData)

        setupIntenMain()
        recyclerSetUp()

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val daten = db.userDao().getAllData()
            Log.d("MainActivity","dbresponse: $daten")
            withContext(Dispatchers.Main){
                mainAdapter.setData(daten)
            }
        }
    }

    private fun setupIntenMain(){
        addNew.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    private fun intentEdit(userId: Int, userType: Int){
        startActivity(Intent(this@MainActivity,AddActivity::class.java)
            .putExtra("intent_id",userId)
            .putExtra("intent_type",userType))
    }


    private fun recyclerSetUp(){
        rvData.layoutManager = LinearLayoutManager(applicationContext)
        mainAdapter = MainAdapter(arrayListOf(),object : MainAdapter.OnAdapterListener{
            override fun setRead(user: User) {
                //read detail data
                intentEdit(user.id,Constant.TYPE_READ)
            }
            override fun setUpdate(user: User) {
                //update data
                intentEdit(user.id,Constant.TYPE_UPDATE)
            }
            override fun setDelete(user: User) {
                deleteDialog(user)
            }
        })
        rvData.adapter = mainAdapter
    }

    private fun deleteDialog(user: User){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Achtung!")
            setMessage("Bist du sicher?")
            setNegativeButton("Nein", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Ja", DialogInterface.OnClickListener { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteData(user)
                    loadData()
                }
            })

        }
        alertDialog.show()
    }
}