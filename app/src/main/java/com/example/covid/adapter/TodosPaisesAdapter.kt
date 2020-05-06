package com.example.covid19.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.R
import com.example.covid.details.DetailsTodosPaisesActivity
import com.example.covid19.entities.TodosPaises
import kotlinx.android.synthetic.main.card_todos_paises.view.*

class TodosPaisesAdapter(private val paises: List<TodosPaises>):
    RecyclerView.Adapter<TodosPaisesAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosPaisesAdapter.VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_todos_paises, parent, false)
        val vh =VH(v)

        vh.itemView.setOnClickListener{
            val paises = paises[vh.adapterPosition]
            val intent = Intent(v.context, DetailsTodosPaisesActivity::class.java)
        }

        return  vh
    }

    override fun getItemCount(): Int {
        return paises.size
    }

    override fun onBindViewHolder(holder: TodosPaisesAdapter.VH, position: Int) {
        var pais = paises[position]
        holder.txtCountry.text = pais.country
        holder.txtNumCaes.text = pais.cases.toString()
    }

    class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var txtCountry: TextView = itemView.txtPais
        var txtNumCaes: TextView = itemView.txtNumCasos
    }

}