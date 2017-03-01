package br.com.pdt.jpaunmanaged.repository

import javax.inject.Inject
import javax.persistence.EntityManager
import kotlin.reflect.KClass

abstract class Repository<out E : Any, in PK>(val type: KClass<out E>) {

    @Inject private lateinit var em: EntityManager

    fun find(pk: PK?): E? = pk?.let { em.find(type.java, pk) }

    fun <E> save(entity: E): E = entity.apply { em.persist(this) }

    fun <E> remove(entity: E): Unit = em.remove(entity)

}
