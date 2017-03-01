package br.com.pdt.jpaunmanaged.persistence

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence.createEntityManagerFactory

class PersistenceManager {

    @Inject
    lateinit var em: EntityManager

    @Produces @ApplicationScoped
    fun getEntityManagerFactory(): EntityManagerFactory = createEntityManagerFactory("jpaUnmanagedPU")

    @Produces @ApplicationScoped
    fun getEntityManager(emf: EntityManagerFactory): EntityManager = emf.createEntityManager()

    fun close(@Disposes emf: EntityManagerFactory): Unit {
        em.close()
        emf.close()
    }

    fun withinTransaction(block: EntityManager.() -> Unit) = block(em)

}
