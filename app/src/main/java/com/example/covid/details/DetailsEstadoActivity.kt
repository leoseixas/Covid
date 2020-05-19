package com.example.covid.details

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid.R
import com.example.covid.http.EstadoHttp
import com.example.covid19.entities.Estado
import kotlinx.android.synthetic.main.activity_details_estado.*

class DetailsEstadoActivity : AppCompatActivity() {

    var uf : String = "RJ"
    private var asyncTask: EstadoTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_estado)

        uf = intent.getStringExtra("uf")
        carregarDados()
    }

    fun carregarDados(){
        if (asyncTask==null){
            if (EstadoHttp.hasConnetcion(this)){
                if (asyncTask?.status!=AsyncTask.Status.RUNNING){
                    asyncTask = EstadoTask()
                    asyncTask?.execute()
                }
            }
        }
    }

    inner class EstadoTask: AsyncTask<Void,Void,Estado?>(){
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): Estado? {
            return EstadoHttp.loadEstados(uf)
        }

        private fun update(result: Estado?){
            if (result != null){
                txtEstado.text = result.states
                txtData.text = result.data
                txtHora.text= result.hora
                txtSuspeitos.text = result.suspects.toString()
                txtMortes.text = result.deaths.toString()
                txtDescartados.text = result.refuses.toString()
                txtTotal.text = result.cases.toString()
            }

            asyncTask = null
        }

        override fun onPostExecute(result: Estado?) {
            super.onPostExecute(result)
            update(result as Estado?)
        }

    }
}
