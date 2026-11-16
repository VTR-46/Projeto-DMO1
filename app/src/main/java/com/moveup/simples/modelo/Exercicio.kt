package com.moveup.simples.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

// Cada objeto representa uma linha da tabela do Room.
@Entity(tableName = "exercicios")
data class Exercicio(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String = "",
    val descricao: String = "",
    val series: Int = 3,
    val repeticoes: Int = 12,
    val imagemUri: String,
    // Nos exercícios iniciais, identifica os textos traduzidos em strings.xml.
    val codigoPadrao: String = ""
)
