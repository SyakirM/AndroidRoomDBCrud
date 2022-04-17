package com.sygame.roomdatabasecrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sygame.roomdatabasecrud.listener.OnClickListener
import com.sygame.roomdatabasecrud.room.User
import org.w3c.dom.Text

class MainAdapter(private val dataInput: ArrayList<User>,private val listener: OnAdapterListener):
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    /*
    private var onClickListener: OnClickListener? = null

    fun setListener(listener: OnClickListener) {
        this.onClickListener = listener
    }
     */


    class MainViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        /* Cara lain:
        fun bindItems(user: User){
            var judulView: TextView = itemView.findViewById(R.id.judulView)
            var deskripsiView: TextView = itemView.findViewById(R.id.deskirpsiView)

            judulView.text = user.judul
            deskripsiView.text = user.deskripsi
        }
         */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_adapter,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = dataInput[position]
        holder.itemView.findViewById<TextView>(R.id.judulView).text = data.judul
        holder.itemView.findViewById<TextView>(R.id.deskirpsiView).text = data.deskripsi

        holder.itemView.findViewById<TextView>(R.id.judulView).setOnClickListener {
            listener.setRead(data)
        }
        holder.itemView.findViewById<ImageView>(R.id.setEdit).setOnClickListener {
            listener.setUpdate(data)
        }
        holder.itemView.findViewById<ImageView>(R.id.setDelete).setOnClickListener {
            listener.setDelete(data)
        }
    }

    override fun getItemCount(): Int = dataInput.size

    fun setData(list: List<User>){
        dataInput.clear()
        dataInput.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun setRead (user: User)
        fun setUpdate (user: User)
        fun setDelete (user: User)
    }
}