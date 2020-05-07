package com.example.covid19.entities

class TodosEstados(
    val uid: Int=0,
    val uf: String,
    val state: String,
    val cases: Int=0,
    val deaths: Int=0,
    val suspects: Int=0,
    val refuses: Int=0,
    val broadcast: Boolean,
    val comments: String,
    val data: String,
    val hora: String
) {

}