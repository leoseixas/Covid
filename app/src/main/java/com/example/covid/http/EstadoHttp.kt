package com.example.covid.http

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.covid19.entities.Estado
import com.example.covid19.entities.TodosEstados
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object EstadoHttp {

    val Json_URL = "https://covid19-brazil-api.now.sh/api/report/v1/brazil/uf/"

    fun hasConnetcion(ctx: Context): Boolean {
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun loadEstados(uf: String): Estado? {
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(Json_URL+uf)
            .build()
        val response = client.newCall(request).execute()
        val jsonString = response.body?.string()
        val jsonO = JSONObject(jsonString)
        // val json = JSONArray(jsonString)
//        val jsonA = jsonO.getJSONArray("data")
        return readJson(jsonO)
    }

    fun readJson(json: JSONObject): Estado? {
        try {

            val data = formatarData(json.getString("datetime"))
            val hora = formatarHora(json.getString("datetime"))

            var estado = Estado(
                json.getInt("uid"),
                json.getString("uf"),
                json.getString("state"),
                json.getInt("cases") ,
                json.getInt("deaths") ,
                json.getInt("suspects") ,
                json.getInt("refuses") ,
                data,
                hora
            )

            return estado
        } catch (e: IOException){
            Log.e("Error", "Impossivel ler Json")
        }
        return null
    }

    fun formatarData(data: String): String {
        val diaString = data.substring(0, 10)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(diaString)
        var formattedDate = date.format(formatter)
        return formattedDate
    }

    fun formatarHora(data: String): String {
        val horaString = data.substring(11, 16)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val date = LocalTime.parse(horaString)
        val formattedHora = date.format(formatter)
        return formattedHora
    }

}