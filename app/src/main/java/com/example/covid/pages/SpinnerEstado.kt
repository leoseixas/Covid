package com.example.covid.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.covid.R

class SpinnerEstado : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner_estado)

        option = findViewById(R.id.spinner) as Spinner
        result = findViewById(R.id.txtResult) as TextView

        val options = arrayOf("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
            "Espírito Santo","Goías")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "selecione um estado"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result.text = options.get(position)
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
              
            }

        }


    }
}
