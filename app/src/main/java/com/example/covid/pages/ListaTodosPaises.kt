package com.example.covid19.pages

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid.R
import com.example.covid19.adapter.TodosPaisesAdapter
import com.example.covid19.entities.TodosPaises
import com.example.covid19.http.TodosPaisesHttp
import kotlinx.android.synthetic.main.activity_lista_todos_paises.*

class ListaTodosPaises : AppCompatActivity() {

    var paisesList = arrayListOf<TodosPaises>()
    var adapter = TodosPaisesAdapter(paisesList)
    private var asyncTask : PaisesTask?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_todos_paises)
        setSupportActionBar(findViewById(R.id.mytool2))
        carregarDados()
        initRecycleView()
    }

    fun initRecycleView(){
        recycleTodosPaises.adapter=adapter
        val layout =LinearLayoutManager(this)
        recycleTodosPaises.layoutManager=layout
    }

    fun showProgress(show: Boolean){
        if (show){
            txtMsg.text
        }else{
            txtMsg.visibility = if(show) View.VISIBLE else View.GONE
            progressBar.visibility = if(show) View.VISIBLE else View.GONE
        }
    }

    fun carregarDados(){
        paisesList.clear()
        if (paisesList.isNotEmpty()){
            showProgress(false)
        }else{
            if (asyncTask==null){
                //startDowload()
                if (asyncTask?.status!=AsyncTask.Status.RUNNING){
                    asyncTask = PaisesTask()
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

    private fun updatePaises(result: List<TodosPaises>){
        if (result!=null){
            paisesList.clear()
            paisesList.addAll(result)
        }else{
            txtMsg.text = "Erro ao carregar"
        }
        adapter.notifyDataSetChanged()
        asyncTask=null
    }

    inner class PaisesTask: AsyncTask<Void,Void,List<TodosPaises>?>(){

        override fun onPreExecute() {
            super.onPreExecute()
            showProgress(true)
        }

        override fun doInBackground(vararg params: Void?): List<TodosPaises>? {
            return TodosPaisesHttp.loadTodosPaises()
        }

        override fun onPostExecute(result: List<TodosPaises>?) {
            super.onPostExecute(result)
            showProgress(false)
            if (result!=null){
                updatePaises(result)
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
