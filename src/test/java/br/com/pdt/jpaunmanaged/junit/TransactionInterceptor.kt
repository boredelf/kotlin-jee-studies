package br.com.pdt.jpaunmanaged.junit

import br.com.pdt.jpaunmanaged.junit.TransactionalModes.ROLLBACK
import javax.inject.Inject
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor
import javax.interceptor.InvocationContext
import javax.persistence.EntityManager

@Interceptor
@Transactional
class TransactionInterceptor {

    @Inject lateinit var em: EntityManager

    @AroundInvoke
    fun onTransaction(ctx: InvocationContext): Any? {
        em.transaction.begin()
        try {
            return ctx.proceed()
        } finally {
            when (ctx.method.getDeclaredAnnotationsByType(Transactional::class.java).first().value) {
                ROLLBACK -> em.transaction.rollback()
                else -> em.transaction.commit()
            }
        }
    }

}