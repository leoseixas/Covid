package com.example.covid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.R
import com.example.covid.entities.CasosRegistro
import kotlinx.android.synthetic.main.card_casos_registrados.view.*

class CasosRegistroAdapter(private val casosRegistrados: List<CasosRegistro>):
        RecyclerView.Adapter<CasosRegistroAdapter.VH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasosRegistroAdapter.VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_casos_registrados, parent, false)
        val vh = VH(v)
        return vh
    }

    override fun getItemCount(): Int {
        return casosRegistrados.size
    }

    override fun onBindViewHolder(holder: CasosRegistroAdapter.VH, position: Int) {
        var casoRegistrado = casosRegistrados[position]
        holder.txtEstado.text = casoRegistrado.state
        holder.txtNumCasos.text = casoRegistrado.cases.toString()
    }

    class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var txtEstado: TextView = itemView.txtEstado
        var txtNumCasos: TextView = itemView.txtNumCasos
    }
}
