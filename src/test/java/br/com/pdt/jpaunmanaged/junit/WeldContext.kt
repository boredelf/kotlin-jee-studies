package br.com.pdt.jpaunmanaged.junit

import org.jboss.weld.environment.se.Weld
import org.jboss.weld.environment.se.WeldContainer

object WeldContext {

    @JvmStatic
    val container: WeldContainer = Weld().initialize()

    fun <T> getBean(cl4ss: Class<T>): T = container.instance().select(cl4ss).get()

}