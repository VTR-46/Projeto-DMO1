package com.moveup.simples.dados

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class BancoDados_Impl : BancoDados() {
  private val _exercicioDao: Lazy<ExercicioDao> = lazy {
    ExercicioDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "2b98ceba525f0bfdd3c4c96501c3113f", "9c4252ee772d19a64f0b7ef3e6542b78") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `exercicios` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `descricao` TEXT NOT NULL, `series` INTEGER NOT NULL, `repeticoes` INTEGER NOT NULL, `imagemUri` TEXT NOT NULL, `codigoPadrao` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2b98ceba525f0bfdd3c4c96501c3113f')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `exercicios`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsExercicios: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExercicios.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExercicios.put("nome", TableInfo.Column("nome", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExercicios.put("descricao", TableInfo.Column("descricao", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExercicios.put("series", TableInfo.Column("series", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExercicios.put("repeticoes", TableInfo.Column("repeticoes", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercicios.put("imagemUri", TableInfo.Column("imagemUri", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExercicios.put("codigoPadrao", TableInfo.Column("codigoPadrao", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExercicios: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesExercicios: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoExercicios: TableInfo = TableInfo("exercicios", _columnsExercicios,
            _foreignKeysExercicios, _indicesExercicios)
        val _existingExercicios: TableInfo = read(connection, "exercicios")
        if (!_infoExercicios.equals(_existingExercicios)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |exercicios(com.moveup.simples.modelo.Exercicio).
              | Expected:
              |""".trimMargin() + _infoExercicios + """
              |
              | Found:
              |""".trimMargin() + _existingExercicios)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "exercicios")
  }

  public override fun clearAllTables() {
    super.performClear(false, "exercicios")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ExercicioDao::class, ExercicioDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun exercicioDao(): ExercicioDao = _exercicioDao.value
}
