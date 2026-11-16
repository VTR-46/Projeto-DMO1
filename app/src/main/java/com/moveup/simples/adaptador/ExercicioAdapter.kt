package com.moveup.simples.adaptador

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moveup.simples.R
import com.moveup.simples.dados.CatalogoExercicios
import com.moveup.simples.databinding.ItemExercicioBinding
import com.moveup.simples.modelo.Exercicio
import com.moveup.simples.telas.RecursosTela

class ExercicioAdapter(
    private var exercicios: List<Exercicio>,
    private val aoClicar: (Exercicio) -> Unit
) : RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder>() {

    // Nomes dos métodos override são definidos pelo RecyclerView.
    override fun onCreateViewHolder(pai: ViewGroup, tipo: Int): ExercicioViewHolder {
        val vinculacao = ItemExercicioBinding.inflate(LayoutInflater.from(pai.context), pai, false)
        return ExercicioViewHolder(vinculacao)
    }

    override fun onBindViewHolder(suporte: ExercicioViewHolder, posicao: Int) {
        suporte.preencher(exercicios[posicao])
    }

    override fun getItemCount(): Int = exercicios.size

    @SuppressLint("NotifyDataSetChanged")
    fun atualizarLista(novaLista: List<Exercicio>) {
        exercicios = novaLista
        // A lista é pequena; esta atualização segue a abordagem da aula.
        notifyDataSetChanged()
    }

    inner class ExercicioViewHolder(private val vinculacao: ItemExercicioBinding) :
        RecyclerView.ViewHolder(vinculacao.root) {

        fun preencher(exercicio: Exercicio) {
            val contexto = vinculacao.root.context
            vinculacao.tvNome.text = CatalogoExercicios.obterNome(contexto, exercicio)
            vinculacao.tvResumo.text = contexto.getString(
                R.string.resumo_exercicio, exercicio.series, exercicio.repeticoes
            )
            RecursosTela.mostrarImagem(vinculacao.imgExercicio, exercicio.imagemUri)
            vinculacao.root.setOnClickListener { aoClicar(exercicio) }
        }
    }
}
