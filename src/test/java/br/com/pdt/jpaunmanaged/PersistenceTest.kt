package br.com.pdt.jpaunmanaged

import br.com.pdt.jpaunmanaged.entity.Pessoa
import br.com.pdt.jpaunmanaged.entity.Telefone
import br.com.pdt.jpaunmanaged.junit.WeldJUnit4Runner
import br.com.pdt.jpaunmanaged.persistence.PersistenceManager
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.validation.ConstraintViolationException

@RunWith(WeldJUnit4Runner::class)
class PersistenceTest {

    @Inject
    private lateinit var pm: PersistenceManager

    @Test
    fun criarPessoaSemTelefones() = pm.withinTransaction {
        val pessoa = Pessoa("Shino", 28).apply { persist(this) }
        assertNotNull(find(Pessoa::class.java, pessoa.id))
    }

    @Test
    fun criarPessoaComTelefones() = pm.withinTransaction {
        val telefone = Telefone("3333-3333")
        val pessoa = Pessoa("Shino", 28, listOf(telefone)).apply { persist(this) }
        assertEquals(find(Pessoa::class.java, pessoa.id).telefones.first().numero, telefone.numero)
    }

    @Test
    fun removerPessoaDeveRemoverTelefones() = pm.withinTransaction {
        val telefone = Telefone("3333-3333")
        remove(Pessoa("Shino", 28, listOf(telefone)).apply { persist(this) })
        assertNull(find(Telefone::class.java, telefone.id))
    }

    @Test(expected = ConstraintViolationException::class)
    fun criarPessoaComIdadeAbaixoDoMinimo() = pm.withinTransaction {
        persist(Pessoa("Shino", 0))
        flush()
    }

    @Test(expected = ConstraintViolationException::class)
    fun criarTelefoneComNumeroInvalido() = pm.withinTransaction {
        persist(Telefone("234-0001"))
        flush()
    }

    @Test(expected = ConstraintViolationException::class)
    fun criarPessoaComTelefoneInvalido() = pm.withinTransaction {
        persist(Pessoa("Shino", 28, listOf(Telefone("234-0001"))))
        flush()
    }

}
