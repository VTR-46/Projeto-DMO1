package com.moveup.simples

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moveup.simples.dados.BancoDados
import com.moveup.simples.modelo.Exercicio
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersistenciaTest {
    private val contexto = ApplicationProvider.getApplicationContext<Context>()
    private val nomeBanco = "teste_moveup.db"
    private lateinit var banco: BancoDados

    @Before fun prepararBanco() {
        contexto.deleteDatabase(nomeBanco)
        banco = BancoDados.criarBanco(contexto, nomeBanco)
    }

    @After fun apagarBanco() {
        banco.close()
        contexto.deleteDatabase(nomeBanco)
    }

    @Test fun cadastrarEditarReabrirExcluir() = runBlocking {
        val dao = banco.exercicioDao()
        val uri = "content://exemplo/imagens/42"
        val id = dao.inserir(Exercicio(nome = "Teste", imagemUri = uri))
        val cadastrado = requireNotNull(dao.buscarPorId(id))
        dao.atualizar(cadastrado.copy(nome = "Editado", series = 4))
        banco.close()
        banco = BancoDados.criarBanco(contexto, nomeBanco)
        val recuperado = requireNotNull(banco.exercicioDao().buscarPorId(id))
        assertEquals("Editado", recuperado.nome)
        assertEquals(4, recuperado.series)
        assertEquals(uri, recuperado.imagemUri)
        banco.exercicioDao().excluir(id)
        assertNull(banco.exercicioDao().buscarPorId(id))
    }

    @Test fun catalogoExcluidoNaoReaparece() = runBlocking {
        val dao = banco.exercicioDao()
        val exercicios = dao.listar()
        assertEquals(4, exercicios.size)
        for (exercicio in exercicios) dao.excluir(exercicio.id)
        banco.close()
        banco = BancoDados.criarBanco(contexto, nomeBanco)
        assertTrue(banco.exercicioDao().listar().isEmpty())
    }
}
