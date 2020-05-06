package com.example.covid19.entities

class TodosEstados(
    var udi: Int=0,
    var uf: String,
    var state: String,
    var deaths: Int=0,
    var suspects: Int=0,
    var broadcast: Boolean,
    var comments: String,
    var data: String,
    var hora: String
) {

}