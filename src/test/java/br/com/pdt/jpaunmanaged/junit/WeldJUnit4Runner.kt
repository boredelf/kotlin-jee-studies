package br.com.pdt.jpaunmanaged.junit

import org.junit.runners.BlockJUnit4ClassRunner

class WeldJUnit4Runner(klass: Class<*>?) : BlockJUnit4ClassRunner(klass) {

    override fun createTest(): Any = WeldContext.getBean(testClass.javaClass)

}