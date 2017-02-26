package br.com.pdt.jpaunmanaged.persistence

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence.createEntityManagerFactory

class PersistenceManager(
    persistenceUnit: String,
    private val emf: EntityManagerFactory = createEntityManagerFactory(persistenceUnit)
) {

    fun close(): Unit = emf.close()

    fun withinTransaction(block: EntityManager.() -> Unit): Unit =
        emf.createEntityManager().let { em ->
            try {
                em.transaction.begin()
                block(em)
            } finally {
                em.transaction.rollback()
                em.close()
            }
        }

}
