package com.example.covid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.covid.pages.ListaTodosEstados
import com.example.covid19.pages.ListaTodosPaises
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }
}
