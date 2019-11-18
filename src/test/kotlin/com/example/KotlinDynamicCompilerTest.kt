package com.example

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
        for (i in 1..3) {
            try {
                val outputDir = Files.createTempDirectory("out").toFile()
                val sourcePack = javaClass.getResource("/testPack$i").file
                println("source $sourcePack")
                println("outputDir $outputDir")
                kotlinDynamicCompiler.compileScript("testPack$i", sourcePack, outputDir);
                val uri = arrayOf(outputDir.toURI().toURL())
                val classLoader = URLClassLoader.newInstance(uri)
                val clazz = classLoader.loadClass("com.example.kt.SimpleClass")
            } catch (e: Exception) {
                e.printStackTrace()
                errorCount++;
            }
        }
        assertEquals(0, errorCount)
    }

}
