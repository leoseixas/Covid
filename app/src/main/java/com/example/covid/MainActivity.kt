package com.example.covid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.covid.details.DetailsEstadoActivity
import com.example.covid.pages.CalendarViewCasos
import com.example.covid.pages.ListaTodosEstados
import com.example.covid19.pages.ListaTodosPaises
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var state : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTodosPais.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ListaTodosPaises::class.java)
            startActivity(intent)
        })
        btnTodosEstados.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, ListaTodosEstados::class.java)
            startActivity(intent1)
        })

        option = findViewById(R.id.spinner) as Spinner
        state = findViewById(R.id.txtUf) as TextView

        val options = resources.getStringArray(R.array.uf)
        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options)
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                state.text = "selecione um estado"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                state.text = options.get(position)
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }
        }


        btnEstado.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(this, DetailsEstadoActivity::class.java)
            val uf = state.text.toString()
            intent2.putExtra("uf",uf)
            startActivity(intent2)
        })

        btnRegistro.setOnClickListener(View.OnClickListener {
            val intent3 = Intent(this, CalendarViewCasos::class.java)
            startActivity(intent3)
        })
    }
}
