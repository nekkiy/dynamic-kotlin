package com.example

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoot
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.codegen.state.GenerationState
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.config.JvmTarget
import java.io.File
import kotlin.script.experimental.jvm.util.KotlinJars

class KotlinDynamicCompiler {
    fun compileScript(moduleName: String,
                      sourcePath: String,
                      saveClassesDir: File
    ): GenerationState {
        val stubDisposable = StubDisposable();
        val configuration = CompilerConfiguration()
        configuration.put(CommonConfigurationKeys.MODULE_NAME, moduleName)
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, PrintingMessageCollector(System.out, MessageRenderer.PLAIN_FULL_PATHS, true))
        configuration.put(JVMConfigurationKeys.OUTPUT_DIRECTORY, saveClassesDir)
        configuration.put(JVMConfigurationKeys.JVM_TARGET, JvmTarget.JVM_1_8)
        configuration.addKotlinSourceRoot(sourcePath)
        configuration.addJvmClasspathRoots(listOf(KotlinJars.stdlib))
        val env = KotlinCoreEnvironment.createForProduction(stubDisposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES)
        return KotlinToJVMBytecodeCompiler.analyzeAndGenerate(env)!!;
    }

    inner class StubDisposable : Disposable {
        @Volatile
        var isDisposed: Boolean = false
            private set

        override fun dispose() {
            isDisposed = true
        }

    };

}
