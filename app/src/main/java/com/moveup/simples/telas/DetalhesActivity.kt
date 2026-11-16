package com.moveup.simples.telas

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.moveup.simples.R
import com.moveup.simples.dados.BancoDados
import com.moveup.simples.dados.CatalogoExercicios
import com.moveup.simples.databinding.ActivityDetalhesBinding
import com.moveup.simples.modelo.Exercicio
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class DetalhesActivity : AppCompatActivity() {
    companion object { const val CHAVE_ID = "id_exercicio" }

    private lateinit var vinculacao: ActivityDetalhesBinding
    private var exercicio: Exercicio? = null

    override fun onCreate(estadoSalvo: Bundle?) {
        super.onCreate(estadoSalvo)
        enableEdgeToEdge()
        vinculacao = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(vinculacao.root)
        RecursosTela.ajustarMargens(vinculacao.root)
        configurarBotoes()
    }

    override fun onResume() {
        super.onResume()
        carregarExercicio()
    }

    private fun configurarBotoes() {
        vinculacao.barraTitulo.setNavigationOnClickListener { finish() }
        vinculacao.btnEditar.setOnClickListener {
            val selecionado = exercicio ?: return@setOnClickListener
            val intencao = Intent(this, CadastroActivity::class.java)
            intencao.putExtra(CHAVE_ID, selecionado.id)
            startActivity(intencao)
        }
        vinculacao.btnExcluir.setOnClickListener { confirmarExclusao() }
        vinculacao.btnCompartilhar.setOnClickListener { compartilharExercicio() }
        vinculacao.btnSite.setOnClickListener {
            // Intent implícita: o Android escolhe o navegador disponível
            abrirAplicativo(Intent(Intent.ACTION_VIEW, "https://en.wikipedia.org/wiki/Strength_training".toUri()))
        }
    }

    private fun carregarExercicio() {
        vinculacao.rolagem.isVisible = false
        lifecycleScope.launch {
            try {
                val id = intent.getLongExtra(CHAVE_ID, 0)
                exercicio = BancoDados.obterInstancia(this@DetalhesActivity).exercicioDao().buscarPorId(id)
                val selecionado = exercicio
                if (selecionado == null) {
                    mostrarMensagem(R.string.exercicio_nao_encontrado)
                    finish()
                    return@launch
                }
                vinculacao.tvNome.text = CatalogoExercicios.obterNome(this@DetalhesActivity, selecionado)
                vinculacao.tvDescricao.text = CatalogoExercicios.obterDescricao(this@DetalhesActivity, selecionado)
                vinculacao.tvResumo.text = getString(R.string.resumo_exercicio, selecionado.series, selecionado.repeticoes)
                RecursosTela.mostrarImagem(vinculacao.imgExercicio, selecionado.imagemUri)
                vinculacao.rolagem.isVisible = true
            } catch (erro: Exception) {
                if (erro is CancellationException) throw erro
                mostrarMensagem(R.string.erro_carregar)
                finish()
            }
        }
    }

    private fun confirmarExclusao() {
        val selecionado = exercicio ?: return
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.confirmar_exclusao)
            .setMessage(getString(R.string.mensagem_exclusao, CatalogoExercicios.obterNome(this, selecionado)))
            .setNegativeButton(R.string.cancelar, null)
            .setPositiveButton(R.string.excluir) { _, _ -> excluirExercicio(selecionado.id) }
            .show()
    }

    private fun excluirExercicio(id: Long) {
        vinculacao.btnExcluir.isEnabled = false
        lifecycleScope.launch {
            try {
                BancoDados.obterInstancia(this@DetalhesActivity).exercicioDao().excluir(id)
                finish()
            } catch (erro: Exception) {
                if (erro is CancellationException) throw erro
                mostrarMensagem(R.string.erro_excluir)
                vinculacao.btnExcluir.isEnabled = true
            }
        }
    }

    private fun compartilharExercicio() {
        val selecionado = exercicio ?: return
        val mensagem = getString(R.string.texto_compartilhamento,
            CatalogoExercicios.obterNome(this, selecionado),
            getString(R.string.resumo_exercicio, selecionado.series, selecionado.repeticoes),
            CatalogoExercicios.obterDescricao(this, selecionado))
        val intencao = Intent(Intent.ACTION_SEND)
        intencao.type = "text/plain"
        intencao.putExtra(Intent.EXTRA_TEXT, mensagem)
        abrirAplicativo(Intent.createChooser(intencao, getString(R.string.compartilhar_exercicio)))
    }

    private fun abrirAplicativo(intencao: Intent) {
        try { startActivity(intencao) }
        catch (_: ActivityNotFoundException) { mostrarMensagem(R.string.erro_aplicativo) }
    }

    private fun mostrarMensagem(texto: Int) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }
}
