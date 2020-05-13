package com.example.covid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.R
import com.example.covid.details.DetailsTodosEstadosActivity
import com.example.covid19.entities.TodosEstados
import kotlinx.android.synthetic.main.card_todos_estados.view.*

class TodosEstadosAdapter(private val estados: List<TodosEstados>):
    RecyclerView.Adapter<TodosEstadosAdapter.VH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosEstadosAdapter.VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_todos_estados, parent, false)
        val vh = VH(v)

        vh.itemView.setOnClickListener{
            val estados = estados[vh.adapterPosition]
            val intent = Intent(v.context, DetailsTodosEstadosActivity::class.java)
            intent.putExtra("state", estados.state)
            intent.putExtra("data", estados.data)
            intent.putExtra("hora", estados.hora)
            intent.putExtra("susp   ects", estados.suspects.toString())
            intent.putExtra("deaths", estados.deaths.toString())
            intent.putExtra("refuses", estados.refuses.toString())
            intent.putExtra("cases", estados.cases.toString())
            v.context.startActivity(intent)
        }
        return vh
    }

    override fun getItemCount(): Int {
        return estados.size
    }

    override fun onBindViewHolder(holder: TodosEstadosAdapter.VH, position: Int) {
        var estado = estados[position]
        holder.txtEstado.text = estado.state
        holder.txtCasos.text = estado.cases.toString()
    }

    class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var txtEstado: TextView = itemView.txtEstado
        var txtCasos: TextView = itemView.txtCasosEstados
    }

}