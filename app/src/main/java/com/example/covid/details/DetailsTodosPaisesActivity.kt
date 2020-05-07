package com.example.covid.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid.R
import kotlinx.android.synthetic.main.activity_details_todos_paises.*

class DetailsTodosPaisesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_todos_paises)
        txtNomePais.text = intent.getStringExtra("country")
        txtData.text = intent.getStringExtra("data")
        txtHora.text = intent.getStringExtra("hora")
        txtNumConf.text = intent.getStringExtra("confirmed")
        txtNumMortes.text = intent.getStringExtra("deaths")
        txtNumCurados.text = intent.getStringExtra("recovered")
        txtTotalCasos.text = intent.getStringExtra("cases")
    }
}
