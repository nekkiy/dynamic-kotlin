package com.example

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngineFactory
import java.lang.Exception
import java.net.URLClassLoader
import java.nio.file.Files

import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinDynamicCompilerTest {


    @Test
    fun testSomeLibraryMethod() {
        val kotlinDynamicCompiler = KotlinDynamicCompiler()
        var errorCount = 0
        val classLoaders = mutableListOf<ClassLoader>()

        for (i in 1..3) {
            try {
                val outputDir = Files.createTempDirectory("out").toFile()
                val testPack = "/testPack$i";
                val sourcePack = javaClass.getResource(testPack).file
                kotlinDynamicCompiler.compileModule("testPack$i", listOf(sourcePack), outputDir, Thread.currentThread().contextClassLoader);
                val uri = arrayOf(outputDir.toURI().toURL())
                val classLoader = URLClassLoader.newInstance(uri)
                classLoaders.add(classLoader)
                classLoader.loadClass("com.example.kt.SimpleClass")
                var str = javaClass.getResourceAsStream("/script$i.kts").reader().use {
                    it.readText()
                }
                val oldCl = Thread.currentThread().contextClassLoader
                try {
                    Thread.currentThread().contextClassLoader = classLoader
                    val engine = KotlinJsr223JvmLocalScriptEngineFactory().scriptEngine
                    engine.eval(str)
                } finally {
                    Thread.currentThread().contextClassLoader = oldCl
                }

            } catch (e: Exception) {
                e.printStackTrace()
                errorCount++;
            }
        }
        assertEquals(0, errorCount)


    }

}
