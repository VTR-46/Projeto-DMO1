package com.moveup.simples.dados

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.moveup.simples.modelo.Exercicio
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ExercicioDao_Impl(
  __db: RoomDatabase,
) : ExercicioDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfExercicio: EntityInsertAdapter<Exercicio>

  private val __updateAdapterOfExercicio: EntityDeleteOrUpdateAdapter<Exercicio>
  init {
    this.__db = __db
    this.__insertAdapterOfExercicio = object : EntityInsertAdapter<Exercicio>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `exercicios` (`id`,`nome`,`descricao`,`series`,`repeticoes`,`imagemUri`,`codigoPadrao`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Exercicio) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nome)
        statement.bindText(3, entity.descricao)
        statement.bindLong(4, entity.series.toLong())
        statement.bindLong(5, entity.repeticoes.toLong())
        statement.bindText(6, entity.imagemUri)
        statement.bindText(7, entity.codigoPadrao)
      }
    }
    this.__updateAdapterOfExercicio = object : EntityDeleteOrUpdateAdapter<Exercicio>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `exercicios` SET `id` = ?,`nome` = ?,`descricao` = ?,`series` = ?,`repeticoes` = ?,`imagemUri` = ?,`codigoPadrao` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Exercicio) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nome)
        statement.bindText(3, entity.descricao)
        statement.bindLong(4, entity.series.toLong())
        statement.bindLong(5, entity.repeticoes.toLong())
        statement.bindText(6, entity.imagemUri)
        statement.bindText(7, entity.codigoPadrao)
        statement.bindLong(8, entity.id)
      }
    }
  }

  public override suspend fun inserir(exercicio: Exercicio): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfExercicio.insertAndReturnId(_connection, exercicio)
    _result
  }

  public override suspend fun atualizar(exercicio: Exercicio): Int = performSuspending(__db, false,
      true) { _connection ->
    var _result: Int = 0
    _result += __updateAdapterOfExercicio.handle(_connection, exercicio)
    _result
  }

  public override suspend fun listar(): List<Exercicio> {
    val _sql: String = "SELECT * FROM exercicios ORDER BY id DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfDescricao: Int = getColumnIndexOrThrow(_stmt, "descricao")
        val _columnIndexOfSeries: Int = getColumnIndexOrThrow(_stmt, "series")
        val _columnIndexOfRepeticoes: Int = getColumnIndexOrThrow(_stmt, "repeticoes")
        val _columnIndexOfImagemUri: Int = getColumnIndexOrThrow(_stmt, "imagemUri")
        val _columnIndexOfCodigoPadrao: Int = getColumnIndexOrThrow(_stmt, "codigoPadrao")
        val _result: MutableList<Exercicio> = mutableListOf()
        while (_stmt.step()) {
          val _item: Exercicio
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNome: String
          _tmpNome = _stmt.getText(_columnIndexOfNome)
          val _tmpDescricao: String
          _tmpDescricao = _stmt.getText(_columnIndexOfDescricao)
          val _tmpSeries: Int
          _tmpSeries = _stmt.getLong(_columnIndexOfSeries).toInt()
          val _tmpRepeticoes: Int
          _tmpRepeticoes = _stmt.getLong(_columnIndexOfRepeticoes).toInt()
          val _tmpImagemUri: String
          _tmpImagemUri = _stmt.getText(_columnIndexOfImagemUri)
          val _tmpCodigoPadrao: String
          _tmpCodigoPadrao = _stmt.getText(_columnIndexOfCodigoPadrao)
          _item =
              Exercicio(_tmpId,_tmpNome,_tmpDescricao,_tmpSeries,_tmpRepeticoes,_tmpImagemUri,_tmpCodigoPadrao)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun buscarPorId(id: Long): Exercicio? {
    val _sql: String = "SELECT * FROM exercicios WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfDescricao: Int = getColumnIndexOrThrow(_stmt, "descricao")
        val _columnIndexOfSeries: Int = getColumnIndexOrThrow(_stmt, "series")
        val _columnIndexOfRepeticoes: Int = getColumnIndexOrThrow(_stmt, "repeticoes")
        val _columnIndexOfImagemUri: Int = getColumnIndexOrThrow(_stmt, "imagemUri")
        val _columnIndexOfCodigoPadrao: Int = getColumnIndexOrThrow(_stmt, "codigoPadrao")
        val _result: Exercicio?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNome: String
          _tmpNome = _stmt.getText(_columnIndexOfNome)
          val _tmpDescricao: String
          _tmpDescricao = _stmt.getText(_columnIndexOfDescricao)
          val _tmpSeries: Int
          _tmpSeries = _stmt.getLong(_columnIndexOfSeries).toInt()
          val _tmpRepeticoes: Int
          _tmpRepeticoes = _stmt.getLong(_columnIndexOfRepeticoes).toInt()
          val _tmpImagemUri: String
          _tmpImagemUri = _stmt.getText(_columnIndexOfImagemUri)
          val _tmpCodigoPadrao: String
          _tmpCodigoPadrao = _stmt.getText(_columnIndexOfCodigoPadrao)
          _result =
              Exercicio(_tmpId,_tmpNome,_tmpDescricao,_tmpSeries,_tmpRepeticoes,_tmpImagemUri,_tmpCodigoPadrao)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun excluir(id: Long) {
    val _sql: String = "DELETE FROM exercicios WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
