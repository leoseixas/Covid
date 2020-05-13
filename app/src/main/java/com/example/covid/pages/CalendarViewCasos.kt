package com.example.covid.pages

import android.app.DatePickerDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid.R
import com.example.covid.adapter.CasosRegistroAdapter
import com.example.covid.entities.CasosRegistro
import kotlinx.android.synthetic.main.activity_calendar_view_casos.*
import java.text.SimpleDateFormat
import java.util.*
import com.example.covid.http.CasosRegistroHttp

class CalendarViewCasos : AppCompatActivity() {

    var data: String = "20200513"

    var button_date: Button? = null
    //var textview_date: TextView? = null
    var cal = Calendar.getInstance()

    var casosList = arrayListOf<CasosRegistro>()
    var adapter = CasosRegistroAdapter(casosList)
    private var asyncTask : CasosTask?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_view_casos)
        setSupportActionBar(findViewById(R.id.toolbar))

       // textview_date = this.txtData
        button_date = this.btnData

       // textview_date!!.text = "----/--/--"

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                data = txtData.text.toString()
                carregarDados()
                initRecycleView()
            }
        }

        btnData!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(this@CalendarViewCasos,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
    }


    fun initRecycleView(){
        recycleCasos.adapter=adapter
        val layout =  LinearLayoutManager(this)
        recycleCasos.layoutManager=layout
    }

    fun showProgress(show: Boolean){
        if (show){
            txtCarregar.text = "Carregando"
        }else{
            txtCarregar.visibility = if(show) View.VISIBLE else View.GONE
            progressBar4.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    fun carregarDados(){
        casosList.clear()
        if (casosList.isNotEmpty()){
            showProgress(false)
        }else{
            if (asyncTask==null) {
                if(asyncTask?.status!=AsyncTask.Status.RUNNING){
                    asyncTask = CasosTask()
                    asyncTask?.execute()
                }else{
                    progressBar4.visibility = View.GONE
                    txtCarregar.text = "Erro"
                }
            }else if (asyncTask?.status==AsyncTask.Status.RUNNING){
                showProgress(true)
            }
        }
    }

    private fun updateCasos(result: List<CasosRegistro>){
        if  (result!=null){
            casosList.clear()
            casosList.addAll(result)
        }else{
            txtCarregar.text = "Erro ao carregar"
        }
        adapter.notifyDataSetChanged()
        asyncTask=null
    }

    inner class CasosTask: AsyncTask<Void, Void, List<CasosRegistro>?>(){
        override fun onPreExecute() {
            super.onPreExecute()
            showProgress(true)
        }
        override fun doInBackground(vararg params: Void?): List<CasosRegistro>? {
            return CasosRegistroHttp(data).loadCasosData()
        }

        override fun onPostExecute(result: List<CasosRegistro>?) {
            super.onPostExecute(result)
            showProgress(false)
            if (result!=null){
                updateCasos(result)
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
            Log.e("Erro","Carregar dados")
            true
        }
        else ->{
            super.onOptionsItemSelected(item)
        }
    }

}
