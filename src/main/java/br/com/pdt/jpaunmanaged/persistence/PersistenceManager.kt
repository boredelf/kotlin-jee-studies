package br.com.pdt.jpaunmanaged.persistence

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence.createEntityManagerFactory

class PersistenceManager {

    @Produces
    @ApplicationScoped
    fun getEntityManagerFactory(): EntityManagerFactory = createEntityManagerFactory("jpaUnmanagedPU")

    @Produces
    @ApplicationScoped
    fun getEntityManager(emf: EntityManagerFactory): EntityManager = emf.createEntityManager()

    fun closeEntityManagerFactory(@Disposes emf: EntityManagerFactory): Unit = emf.close()

    fun closeEntityManager(@Disposes em: EntityManager): Unit = em.close()

}
