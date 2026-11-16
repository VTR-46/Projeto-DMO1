package com.moveup.simples.dados

import android.content.Context
import com.moveup.simples.R
import com.moveup.simples.modelo.Exercicio

object CatalogoExercicios {
    fun criarListaInicial(contexto: Context): List<Exercicio> {
        val caminho = "android.resource://${contexto.packageName}/drawable/"
        return listOf(
            Exercicio(codigoPadrao = "agachamento", imagemUri = caminho + "img_agachamento"),
            Exercicio(codigoPadrao = "rosca", imagemUri = caminho + "img_rosca"),
            Exercicio(codigoPadrao = "supino", imagemUri = caminho + "img_mascote"),
            Exercicio(codigoPadrao = "remada", imagemUri = caminho + "img_mascote_feminina")
        )
    }

    fun obterNome(contexto: Context, exercicio: Exercicio): String {
        if (exercicio.nome.isNotEmpty()) return exercicio.nome
        val recurso = when (exercicio.codigoPadrao) {
            "agachamento" -> R.string.nome_agachamento
            "rosca" -> R.string.nome_rosca
            "supino" -> R.string.nome_supino
            "remada" -> R.string.nome_remada
            else -> R.string.nome_exercicio
        }
        return contexto.getString(recurso)
    }

    fun obterDescricao(contexto: Context, exercicio: Exercicio): String {
        if (exercicio.descricao.isNotEmpty()) return exercicio.descricao
        val recurso = when (exercicio.codigoPadrao) {
            "agachamento" -> R.string.descricao_agachamento
            "rosca" -> R.string.descricao_rosca
            "supino" -> R.string.descricao_supino
            "remada" -> R.string.descricao_remada
            else -> R.string.sem_descricao
        }
        return contexto.getString(recurso)
    }
}
