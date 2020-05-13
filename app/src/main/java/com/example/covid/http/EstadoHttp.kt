package com.example.covid.http

import android.content.Context
import android.net.ConnectivityManager
import com.example.covid19.entities.TodosEstados
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object EstadoHttp {

    val Json_URL="https://covid19-brazil-api.now.sh/api/report/v1/brazil/uf/sp"

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
            .url(TodosEstadosHttp.Json_URL)
            .build()
        val response = client.newCall(request).execute()
        val jsonString = response.body?.string()
        val jsonO = JSONObject(jsonString)
        // val json = JSONArray(jsonString)
        val jsonA = jsonO.getJSONArray("data")
        return TodosEstadosHttp.readJson(jsonA)
    }

}