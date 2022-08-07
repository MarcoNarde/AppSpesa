package com.narde.appspesa.model

import com.google.firebase.database.Exclude

data class Prodotto (
    var nome: String = "",
    var prezzoUltimo: Double = 0.0,
    var prezzoMedio: Double = 0.0,
    var genere: String = "",
    var immagine: String = "",
    var isComprato: Boolean = false){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nome" to nome,
            "prezzoUltimo" to prezzoUltimo,
            "prezzoMedio" to prezzoMedio,
            "genere" to genere,
            "immagine" to immagine,
            "isComprato" to isComprato
        )
    }
}