package com.moveup.simples.telas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.moveup.simples.R
import com.moveup.simples.adaptador.ExercicioAdapter
import com.moveup.simples.dados.BancoDados
import com.moveup.simples.dados.CatalogoExercicios
import com.moveup.simples.databinding.ActivityMainBinding
import com.moveup.simples.modelo.Exercicio
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var vinculacao: ActivityMainBinding
    private lateinit var adaptador: ExercicioAdapter
    private var exercicios: List<Exercicio> = emptyList()

    override fun onCreate(estadoSalvo: Bundle?) {
        super.onCreate(estadoSalvo)
        enableEdgeToEdge()
        vinculacao = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vinculacao.root)
        RecursosTela.ajustarMargens(vinculacao.root)
        configurarLista()
        configurarBotoes()
        vinculacao.etBusca.doAfterTextChanged { filtrarExercicios() }
    }

    override fun onResume() {
        super.onResume()
        // Ao voltar do cadastro ou dos detalhes le os dados atualizados no Room
        carregarExercicios()
    }

    private fun configurarLista() {
        adaptador = ExercicioAdapter(exercicios) { exercicio ->
            val intencao = Intent(this, DetalhesActivity::class.java)
            intencao.putExtra(DetalhesActivity.CHAVE_ID, exercicio.id)
            startActivity(intencao)
        }
        vinculacao.rvExercicios.layoutManager = LinearLayoutManager(this)
        vinculacao.rvExercicios.adapter = adaptador
    }

    private fun configurarBotoes() {
        vinculacao.btnNovo.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        vinculacao.btnTentarNovamente.setOnClickListener { carregarExercicios() }
    }

    private fun carregarExercicios() {
        vinculacao.tvSituacao.setText(R.string.carregando)
        vinculacao.tvSituacao.isVisible = true
        vinculacao.btnTentarNovamente.isVisible = false
        lifecycleScope.launch {
            try {
                exercicios = BancoDados.obterInstancia(this@MainActivity).exercicioDao().listar()
                filtrarExercicios()
            } catch (erro: Exception) {
                if (erro is CancellationException) throw erro
                vinculacao.tvSituacao.setText(R.string.erro_carregar)
                vinculacao.tvSituacao.isVisible = true
                vinculacao.btnTentarNovamente.isVisible = true
            }
        }
    }

    private fun filtrarExercicios() {
        val busca = vinculacao.etBusca.text.toString().trim()
        val encontrados = exercicios.filter { exercicio ->
            CatalogoExercicios.obterNome(this, exercicio).contains(busca, ignoreCase = true)
        }
        adaptador.atualizarLista(encontrados)
        vinculacao.tvSituacao.setText(R.string.lista_vazia)
        vinculacao.tvSituacao.isVisible = encontrados.isEmpty()
    }
}
