package br.com.pdt.jpaunmanaged

import br.com.pdt.jpaunmanaged.entity.Pessoa
import br.com.pdt.jpaunmanaged.entity.Telefone
import br.com.pdt.jpaunmanaged.junit.Transactional
import br.com.pdt.jpaunmanaged.junit.TransactionalModes.ROLLBACK
import br.com.pdt.jpaunmanaged.junit.WeldJUnit4Runner
import br.com.pdt.jpaunmanaged.persistence.PersistenceManager
import br.com.pdt.jpaunmanaged.repository.PessoaRepository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.validation.ConstraintViolationException

@RunWith(WeldJUnit4Runner::class)
open class PersistenceTest {

    @Inject open lateinit var pm: PersistenceManager
    @Inject open lateinit var em: EntityManager
    @Inject open lateinit var pessoaRepository: PessoaRepository

    @Test
    open fun criarPessoaSemTelefones(): Unit {
        val pessoa = pessoaRepository.save(Pessoa("Shino", 28))
        assertNotNull(pessoaRepository.find(pessoa.id))
    }

    @Test
    open fun criarPessoaComTelefones() = pm.withinTransaction {
        val telefone = Telefone("3333-3333")
        val pessoa = Pessoa("Shino", 28, listOf(telefone)).apply { persist(this) }
        assertEquals(find(Pessoa::class.java, pessoa.id).telefones.first().numero, telefone.numero)
    }

    @Test
    open fun removerPessoaDeveRemoverTelefones() = pm.withinTransaction {
        val telefone = Telefone("3333-3333")
        remove(Pessoa("Shino", 28, listOf(telefone)).apply { persist(this) })
        assertNull(find(Telefone::class.java, telefone.id))
    }

    @Test(expected = ConstraintViolationException::class)
    open fun criarPessoaComIdadeAbaixoDoMinimo() = pm.withinTransaction {
        persist(Pessoa("Shino", 0))
        flush()
    }

    @Test(expected = ConstraintViolationException::class)
    open fun criarTelefoneComNumeroInvalido() = pm.withinTransaction {
        persist(Telefone("234-0001"))
        flush()
    }

    @Transactional(ROLLBACK)
    @Test(expected = ConstraintViolationException::class)
    open fun criarPessoaComTelefoneInvalido(): Unit = pessoaRepository.run {
        save(Pessoa("Shino", 28, listOf(Telefone("234-0001"))))
        em.flush()
    }

}
