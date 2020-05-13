package com.example.covid.entities

class CasosRegistro(
    var uid: Int=0,
    var uf: String,
    var state: String,
    var cases: Int=0,
    var deaths: Int=0,
    var suspects: Int=0,
    var refuses: Int=0,
    var broadcast: Boolean,
    var comments: String,
    var data: String,
    var Hora: String
) {

}