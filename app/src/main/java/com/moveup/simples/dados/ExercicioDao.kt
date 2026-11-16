package com.moveup.simples.dados

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moveup.simples.modelo.Exercicio

@Dao
interface ExercicioDao {
    // suspend permite consultar o banco sem bloquear a interface.
    @Query("SELECT * FROM exercicios ORDER BY id DESC")
    suspend fun listar(): List<Exercicio>

    @Query("SELECT * FROM exercicios WHERE id = :id")
    suspend fun buscarPorId(id: Long): Exercicio?

    @Insert
    suspend fun inserir(exercicio: Exercicio): Long

    @Update
    suspend fun atualizar(exercicio: Exercicio): Int

    @Query("DELETE FROM exercicios WHERE id = :id")
    suspend fun excluir(id: Long)
}
