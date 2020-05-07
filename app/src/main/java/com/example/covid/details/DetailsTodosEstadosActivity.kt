package com.example.covid.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid.R
import kotlinx.android.synthetic.main.activity_details_todos_estados.*

class DetailsTodosEstadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_todos_estados)
        txtState.text = intent.getStringExtra("state")
        txtDataEstado.text = intent.getStringExtra("data")
        txtHoraEstado.text = intent.getStringExtra("hora")
        txtNumSusp.text = intent.getStringExtra("suspects")
        txtNumMortesEstado.text = intent.getStringExtra("deaths")
        txtDescart.text = intent.getStringExtra("refuses")
        txtTotalCasos.text = intent.getStringExtra("cases")
    }
}
