package com.moveup.simples.dados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moveup.simples.modelo.Exercicio

@Database(entities = [Exercicio::class], version = 1, exportSchema = true)
abstract class BancoDados : RoomDatabase() {
    abstract fun exercicioDao(): ExercicioDao

    companion object {
        @Volatile
        private var instancia: BancoDados? = null

        fun obterInstancia(contexto: Context): BancoDados {
            // todas as telas compartilha o mesmo banc
            return instancia ?: synchronized(this) {
                instancia ?: criarBanco(contexto.applicationContext).also { instancia = it }
            }
        }

        // internal permite testar a criação sem reutilizar o banco aberto pelo aplicativo.
        internal fun criarBanco(contexto: Context, nomeBanco: String = "moveup_simples.db"): BancoDados {
            return Room.databaseBuilder(contexto, BancoDados::class.java, nomeBanco)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(banco: SupportSQLiteDatabase) {
                        super.onCreate(banco)
                        // Executado apenas na criação do arquivo do banco.
                        // Excluir os exercícios não faz o catálogo aparecer novamente.
                        for (exercicio in CatalogoExercicios.criarListaInicial(contexto)) {
                            banco.execSQL(
                                """INSERT INTO exercicios
                                    (nome, descricao, series, repeticoes, imagemUri, codigoPadrao)
                                    VALUES (?, ?, ?, ?, ?, ?)""",
                                arrayOf<Any>(exercicio.nome, exercicio.descricao, exercicio.series,
                                    exercicio.repeticoes, exercicio.imagemUri, exercicio.codigoPadrao)
                            )
                        }
                    }
                })
                .build()
        }
    }
}
