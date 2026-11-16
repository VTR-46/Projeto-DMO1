package com.moveup.simples.telas

import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import coil.load
import com.moveup.simples.R

object RecursosTela {
    fun ajustarMargens(raiz: View) {
        val esquerda = raiz.paddingLeft
        val topo = raiz.paddingTop
        val direita = raiz.paddingRight
        val base = raiz.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(raiz) { componente, espacos ->
            val barras = espacos.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            componente.updatePadding(esquerda + barras.left, topo + barras.top,
                direita + barras.right, base + barras.bottom)
            espacos
        }
        ViewCompat.requestApplyInsets(raiz)
    }

    fun mostrarImagem(componente: ImageView, uri: String) {
        // Coil redimensiona a foto e mostra um ícone se o arquivo for removido.
        componente.load(uri) {
            placeholder(R.drawable.ic_exercicio)
            error(R.drawable.ic_exercicio)
        }
    }
}
