package com.example.covid.pages

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid.R
import com.example.covid.adapter.TodosEstadosAdapter
import com.example.covid.http.TodosEstadosHttp
import com.example.covid19.entities.TodosEstados
import kotlinx.android.synthetic.main.activity_lista_todos_estados.*
import kotlinx.android.synthetic.main.activity_lista_todos_paises.*
import kotlinx.android.synthetic.main.activity_lista_todos_paises.txtMsg

class ListaTodosEstados : AppCompatActivity() {

    var estadosList = arrayListOf<TodosEstados>()
    var adapter = TodosEstadosAdapter(estadosList)
    private var asyncTask : EstadosTask?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_todos_estados)
        setSupportActionBar(findViewById(R.id.mytool2))
        carregarDados()
        initRecycleView()
    }

    fun initRecycleView(){
        recycleTodosEstados.adapter=adapter
        val layout = LinearLayoutManager(this)
        recycleTodosEstados.layoutManager=layout
    }

    fun showProgress(show: Boolean){
        if (show){
            txtMsg.text
        }else{
            txtMsg.visibility = if(show) View.VISIBLE else View.GONE
            progressBar2.visibility = if(show) View.VISIBLE else View.GONE
        }
    }

    fun carregarDados(){
        estadosList.clear()
        if (estadosList.isNotEmpty()){
            showProgress(false)
        }else{
            if (asyncTask==null){
                //startDowload()
                if (asyncTask?.status!=AsyncTask.Status.RUNNING){
                    asyncTask = EstadosTask()
                    asyncTask?.execute()
                }else{
                    progressBar.visibility =View.GONE
                    txtMsg.text = "Erro"
                }
            }else if(asyncTask?.status==AsyncTask.Status.RUNNING){
                showProgress(true)
            }
        }
    }

    private fun updateEstados(result: List<TodosEstados>){
        if (result!=null){
            estadosList.clear()
            estadosList.addAll(result)
        }else{
            txtMsg.text = "Erro ao carregar"
        }
        adapter.notifyDataSetChanged()
        asyncTask=null
    }

    inner class EstadosTask: AsyncTask<Void,Void,List<TodosEstados>?>(){
        override fun onPreExecute() {
            super.onPreExecute()
            showProgress(true)
        }

        override fun doInBackground(vararg params: Void?): List<TodosEstados>? {
            return TodosEstadosHttp.loadTodosEstados()
        }

        override fun onPostExecute(result: List<TodosEstados>?) {
            super.onPostExecute(result)
            showProgress(false)
            if (result!=null){
                updateEstados(result)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_refresh ->{
            carregarDados()
            Log.e("Erro","carregando dados")
            true
        }
        else->{
            super.onOptionsItemSelected(item)
        }
    }
}
