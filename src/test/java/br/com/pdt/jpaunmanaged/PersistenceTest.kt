package br.com.pdt.jpaunmanaged

import br.com.pdt.jpaunmanaged.entity.Pessoa
import br.com.pdt.jpaunmanaged.entity.Telefone
import br.com.pdt.jpaunmanaged.junit.Transactional
import br.com.pdt.jpaunmanaged.junit.TransactionalModes.COMMIT
import br.com.pdt.jpaunmanaged.junit.WeldJUnit4Runner
import br.com.pdt.jpaunmanaged.repository.PessoaRepository
import br.com.pdt.jpaunmanaged.repository.TelefoneRepository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.validation.ConstraintViolationException

@Transactional(COMMIT)
@RunWith(WeldJUnit4Runner::class)
open class PersistenceTest {

    @Inject open lateinit var em: EntityManager
    @Inject open lateinit var pessoaRepository: PessoaRepository
    @Inject open lateinit var telefoneRepository: TelefoneRepository

    @Test
    open fun criarPessoaSemTelefones(): Unit {
        val pessoa = pessoaRepository.save(Pessoa("Shino", 28))
        assertNotNull(pessoaRepository.find(pessoa.id))
    }

    @Test
    open fun criarPessoaComTelefones() = pessoaRepository.run {
        val telefone = Telefone("3333-3333")
        val pessoa = Pessoa("Shino", 28, listOf(telefone)).apply { save(this) }
        assertEquals(find(pessoa.id)?.telefones?.first()?.numero, telefone.numero)
    }

    @Test
    open fun removerPessoaDeveRemoverTelefones() = pessoaRepository.run {
        val telefone = Telefone("3333-3333")
        remove(Pessoa("Shino", 28, listOf(telefone)).apply { save(this) })
        assertNull(telefoneRepository.find(telefone.id))
    }

    @Test(expected = ConstraintViolationException::class)
    open fun criarPessoaComIdadeAbaixoDoMinimo() = pessoaRepository.run {
        save(Pessoa("Shino", 0))
        em.flush()
    }

    @Test(expected = ConstraintViolationException::class)
    open fun criarTelefoneComNumeroInvalido() = telefoneRepository.run {
        save(Telefone("234-0001"))
        em.flush()
    }

    @Test(expected = ConstraintViolationException::class)
    open fun criarPessoaComTelefoneInvalido(): Unit = pessoaRepository.run {
        save(Pessoa("Shino", 28, listOf(Telefone("234-0001"))))
        em.flush()
    }

}
