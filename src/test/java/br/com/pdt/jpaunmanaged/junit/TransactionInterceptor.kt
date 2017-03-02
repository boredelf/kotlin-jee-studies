package br.com.pdt.jpaunmanaged.junit

import br.com.pdt.jpaunmanaged.junit.TransactionalModes.COMMIT
import javax.inject.Inject
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor
import javax.interceptor.InvocationContext
import javax.persistence.EntityManager

@Interceptor
@Transactional
class TransactionInterceptor {

    @Inject lateinit var em: EntityManager

    val transactionalType = Transactional::class.java

    @AroundInvoke
    fun wrapInTransaction(ctx: InvocationContext): Any? {
        em.transaction.begin()
        try {
            return ctx.proceed()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            if (em.transaction.isActive) {
                when (getTransactionMode(ctx)) {
                    COMMIT -> em.transaction.commit()
                    else -> em.transaction.rollback()
                }
            }
        }
    }

    fun getTransactionMode(ctx: InvocationContext): TransactionalModes =
        if (ctx.method.isAnnotationPresent(transactionalType))
            ctx.method.getAnnotation(transactionalType).value
        else if (ctx.method.declaringClass.isAnnotationPresent(transactionalType))
            ctx.method.declaringClass.getAnnotation(transactionalType).value
        else COMMIT

}