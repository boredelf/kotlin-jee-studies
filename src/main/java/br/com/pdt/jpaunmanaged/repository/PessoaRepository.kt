package br.com.pdt.jpaunmanaged.repository

import br.com.pdt.jpaunmanaged.entity.Pessoa
import javax.inject.Inject
import javax.persistence.EntityManager

class PessoaRepository {

    @Inject private lateinit var em: EntityManager

    fun find(id: Int?) = em.find(Pessoa::class.java, id)

    fun save(pessoa: Pessoa): Pessoa = pessoa.apply { em.persist(pessoa) }

}