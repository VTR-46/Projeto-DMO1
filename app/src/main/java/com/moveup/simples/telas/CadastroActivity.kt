package com.moveup.simples.telas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.moveup.simples.R
import com.moveup.simples.dados.BancoDados
import com.moveup.simples.dados.CatalogoExercicios
import com.moveup.simples.databinding.ActivityCadastroBinding
import com.moveup.simples.modelo.Exercicio
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {
    private lateinit var vinculacao: ActivityCadastroBinding
    private var idExercicio = 0L
    private var imagemUri = ""
    private var formularioCarregado = false

    // A galeria vairetona uma URI e nao o arquivo inteiro da imagem
    private val selecionarImagem = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imagemUri = uri.toString()
                RecursosTela.mostrarImagem(vinculacao.imgExercicio, imagemUri)
            } catch (_: SecurityException) {
                mostrarMensagem(R.string.erro_imagem)
            }
        }
    }

    override fun onCreate(estadoSalvo: Bundle?) {
        super.onCreate(estadoSalvo)
        enableEdgeToEdge()
        vinculacao = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(vinculacao.root)
        RecursosTela.ajustarMargens(vinculacao.root)
        idExercicio = intent.getLongExtra(DetalhesActivity.CHAVE_ID, 0)
        imagemUri = "android.resource://$packageName/drawable/img_mascote"
        vinculacao.barraTitulo.setTitle(if (idExercicio == 0L) R.string.novo_exercicio else R.string.editar_exercicio)
        configurarBotoes()
        carregarFormulario(estadoSalvo)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE") // Nome local em português; assinatura da API preservada.
    override fun onSaveInstanceState(estado: Bundle) {
        super.onSaveInstanceState(estado)

        estado.putBoolean("formulario_carregado", formularioCarregado)
        estado.putString("nome", vinculacao.etNome.text.toString())
        estado.putString("descricao", vinculacao.etDescricao.text.toString())
        estado.putString("series", vinculacao.etSeries.text.toString())
        estado.putString("repeticoes", vinculacao.etRepeticoes.text.toString())
        estado.putString("imagem_uri", imagemUri)
    }

    private fun configurarBotoes() {
        vinculacao.barraTitulo.setNavigationOnClickListener { finish() }
        vinculacao.btnImagem.setOnClickListener { selecionarImagem.launch(arrayOf("image/*")) }
        vinculacao.btnSalvar.setOnClickListener { salvarExercicio() }
    }

    private fun carregarFormulario(estadoSalvo: Bundle?) {
        if (estadoSalvo?.getBoolean("formulario_carregado") == true) {
            vinculacao.etNome.setText(estadoSalvo.getString("nome"))
            vinculacao.etDescricao.setText(estadoSalvo.getString("descricao"))
            vinculacao.etSeries.setText(estadoSalvo.getString("series"))
            vinculacao.etRepeticoes.setText(estadoSalvo.getString("repeticoes"))
            imagemUri = estadoSalvo.getString("imagem_uri") ?: imagemUri
            RecursosTela.mostrarImagem(vinculacao.imgExercicio, imagemUri)
            formularioCarregado = true
            return
        }
        if (idExercicio == 0L) {
            vinculacao.etSeries.setText(getString(R.string.valor_numerico, 3))
            vinculacao.etRepeticoes.setText(getString(R.string.valor_numerico, 12))
            formularioCarregado = true
            return
        }
        habilitarFormulario(false)
        lifecycleScope.launch {
            try {
                val exercicio = BancoDados.obterInstancia(this@CadastroActivity).exercicioDao().buscarPorId(idExercicio)
                if (exercicio == null) {
                    mostrarMensagem(R.string.exercicio_nao_encontrado)
                    finish()
                    return@launch
                }
                vinculacao.etNome.setText(CatalogoExercicios.obterNome(this@CadastroActivity, exercicio))
                vinculacao.etDescricao.setText(CatalogoExercicios.obterDescricao(this@CadastroActivity, exercicio))
                vinculacao.etSeries.setText(getString(R.string.valor_numerico, exercicio.series))
                vinculacao.etRepeticoes.setText(getString(R.string.valor_numerico, exercicio.repeticoes))
                imagemUri = exercicio.imagemUri
                RecursosTela.mostrarImagem(vinculacao.imgExercicio, imagemUri)
                formularioCarregado = true
                habilitarFormulario(true)
            } catch (erro: Exception) {
                if (erro is CancellationException) throw erro
                mostrarMensagem(R.string.erro_carregar)
                finish()
            }
        }
    }

    private fun salvarExercicio() {
        if (!formularioCarregado || !vinculacao.btnSalvar.isEnabled) return
        val nome = vinculacao.etNome.text.toString().trim()
        val descricao = vinculacao.etDescricao.text.toString().trim()
        val series = vinculacao.etSeries.text.toString().toIntOrNull()
        val repeticoes = vinculacao.etRepeticoes.text.toString().toIntOrNull()

        if (nome.isBlank()) {
            vinculacao.etNome.error = getString(R.string.erro_nome)
            vinculacao.etNome.requestFocus()
            return
        }
        if (series == null || series !in 1..50) {
            vinculacao.etSeries.error = getString(R.string.erro_series)
            vinculacao.etSeries.requestFocus()
            return
        }
        if (repeticoes == null || repeticoes !in 1..500) {
            vinculacao.etRepeticoes.error = getString(R.string.erro_repeticoes)
            vinculacao.etRepeticoes.requestFocus()
            return
        }

        // ao editar um item inicial os textos passam a ser os definidos pelo usaurio
        val exercicio = Exercicio(idExercicio, nome, descricao, series, repeticoes, imagemUri)
        habilitarFormulario(false)
        vinculacao.btnSalvar.setText(R.string.salvando)
        lifecycleScope.launch {
            try {
                val dao = BancoDados.obterInstancia(this@CadastroActivity).exercicioDao()
                if (idExercicio == 0L) {
                    dao.inserir(exercicio)
                } else {
                    check(dao.atualizar(exercicio) == 1)
                }
                setResult(RESULT_OK)
                finish()
            } catch (erro: Exception) {
                if (erro is CancellationException) throw erro
                mostrarMensagem(R.string.erro_salvar)
                habilitarFormulario(true)
                vinculacao.btnSalvar.setText(R.string.salvar)
            }
        }
    }

    private fun habilitarFormulario(habilitado: Boolean) {
        vinculacao.btnSalvar.isEnabled = habilitado
        vinculacao.btnImagem.isEnabled = habilitado
        vinculacao.etNome.isEnabled = habilitado
        vinculacao.etDescricao.isEnabled = habilitado
        vinculacao.etSeries.isEnabled = habilitado
        vinculacao.etRepeticoes.isEnabled = habilitado
    }

    private fun mostrarMensagem(texto: Int) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }
}
