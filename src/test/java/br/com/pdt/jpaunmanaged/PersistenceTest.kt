package br.com.pdt.jpaunmanaged

import br.com.pdt.jpaunmanaged.entity.Pessoa
import br.com.pdt.jpaunmanaged.entity.Telefone
import br.com.pdt.jpaunmanaged.persistence.PersistenceManager
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.Test
import javax.validation.ConstraintViolationException

class PersistenceTest {

    companion object {
        @JvmStatic val pm = PersistenceManager("jpaUnmanagedPU")
        @JvmStatic @AfterClass fun tearDownDatabase(): Unit = pm.close()
    }

    @Test
    fun criarPessoaSemTelefones() = pm.withinTransaction {
        persist(Pessoa(1, "Shino", 28))
        assertNotNull(find(Pessoa::class.java, 1))
    }

    @Test
    fun criarPessoaComTelefones() = pm.withinTransaction {
        val telefone = Telefone(1, "3333-3333")
        val pessoa = Pessoa(1, "Shino", 28, listOf(telefone)).apply { persist(this) }
        val pessoaSalva = find(Pessoa::class.java, pessoa.id)
        assertEquals(pessoaSalva.telefones[0].numero, telefone.numero)
    }

    @Test
    fun removerPessoaDeveRemoverTelefones() = pm.withinTransaction {
        val telefone = Telefone(1, "3333-3333")
        remove(Pessoa(1, "Shino", 28, listOf(telefone)).apply { persist(this) })
        assertNull(find(Telefone::class.java, telefone.id))
    }

    @Test(expected = ConstraintViolationException::class)
    fun criarPessoaComIdadeAbaixoDoMinimo() = pm.withinTransaction {
        persist(Pessoa(1, "Shino", 0))
        flush()
    }

}
