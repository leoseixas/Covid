package com.example.covid.http

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.covid19.entities.TodosEstados
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object TodosEstadosHttp {

    val Json_URL="https://covid19-brazil-api.now.sh/api/report/v1/"

    fun hasConnetcion(ctx: Context): Boolean {
        val cm =ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info !=null && info.isConnected
    }

    fun loadTodosEstados(): List<TodosEstados>{
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(Json_URL)
            .build()
        val response = client.newCall(request).execute()
        val jsonString = response.body?.string()
        val jsonO = JSONObject(jsonString)
        // val json = JSONArray(jsonString)
        val jsonA = jsonO.getJSONArray("data")
        return readJson(jsonA)
    }

    fun readJson(json: JSONArray): List<TodosEstados>{
        var estados = mutableListOf<TodosEstados>()
        try {
            for (i in 0 .. json.length()-1){
                var js = json.getJSONObject(i)

                val uid = js.optInt("uid")
                val uf = js.optString("uf")
                val state = js.optString("state")
                val cases = js.optInt("cases")
                val deaths = js.optInt("deaths")
                val suspects = js.optInt("suspects")
                val refuses = js.optInt("refuses")
                val broadcast = js.optBoolean("broadcast")
                val comments = js.optString("comments")
                val data = formatarData(js.optString("datetime"))
                val hora = formatarHora(js.optString("datetime"))

                val estado = TodosEstados(uid, uf, state, cases, deaths, suspects, refuses,
                    broadcast, comments, data, hora)
                estados.add(estado)
            }
        }
        catch (e : IOException){
            Log.e("Erro", "Impossivel ler JSON")
        }
        return estados
    }

    fun formatarData(data: String):String{
        val diaString =data.substring(0,10)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(diaString)
        var formattedDate = date.format(formatter)
        return formattedDate
    }

    fun formatarHora(data: String): String{
        val horaString = data.substring(11,16)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val date = LocalTime.parse(horaString)
        val formattedHora = date.format(formatter)
        return formattedHora
    }
}