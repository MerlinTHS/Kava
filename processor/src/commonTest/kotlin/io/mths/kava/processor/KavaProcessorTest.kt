package io.mths.kava.processor

import com.tschuchort.compiletesting.*
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode
import org.intellij.lang.annotations.Language
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import kotlin.test.Test

class KavaProcessorTest {
    @Test
    fun `Fails with wrong supertype`() {
        val result = compile(
            kspWithCompilation = false,
            code = """
            import io.mths.kava.*
            
            @GenerateExtensions("test")
            class TestValidator
        """.trimIndent())

        expectThat(result.exitCode) isEqualTo  ExitCode.COMPILATION_ERROR
    }

    @Test
    fun `Succeeds with valid supertype`() {
        val result = compile("""
            import io.mths.kava.*
            
            @GenerateExtensions("test")
            class TestValidator2 : Validator<String, String> {
                override val invalid = ""
                override fun valid(value: String) = "Valid"
                override fun ValidationScope<*>.validate(wrapper: String) =
                    if (wrapper.isEmpty())
                        invalid
                    else
                        valid(wrapper)
            }
        """.trimIndent())

        expectThat(result.exitCode) isEqualTo ExitCode.OK
    }

    @Test
    fun `Resolves supertype of supertype`() {
        val result = compile("""
            import io.mths.kava.*

            interface TestSuperValidator : Validator<String, String>
            
            @GenerateExtensions("test")
            class TestValidator3 : TestSuperValidator {
                override val invalid = ""
                override fun valid(value: String) = "Valid"
                override fun ValidationScope<*>.validate(wrapper: String) =
                    if (wrapper.isEmpty())
                        invalid
                    else
                        valid(wrapper)
            }
        """.trimIndent())

        expectThat(result.exitCode) isEqualTo ExitCode.OK
    }

     @Test
     fun `Separates files`() {
         val result = compile(
             separateFiles = true,
             kspWithCompilation = true,
             code = """
                 package com.example
                import io.mths.kava.*
            
                @GenerateExtensions("test")
                class TestValidator4 : Validator<String, String> {
                    override val invalid = ""
                    override fun valid(value: String) = "Valid"
                    override fun ValidationScope<*>.validate(wrapper: String) =
                        if (wrapper.isEmpty())
                            invalid
                        else
                            valid(wrapper)
                }
             """.trimIndent()
         )

         expectThat(result.exitCode) isEqualTo ExitCode.OK
         expectThat(result.generatedFiles).isNotEmpty()
     }

    private fun compile(
        @Language("kotlin") code: String,
        separateFiles: Boolean = false,
        kspWithCompilation: Boolean = false
    ): KotlinCompilation.Result {
        val source = SourceFile.kotlin("TestValidator.kt", code)
        val compilation = KotlinCompilation().apply {
            symbolProcessorProviders = listOf(KavaProcessorProvider())
            sources = listOf(source)

            if (separateFiles) {
                kspArgs["Kava.SeparateFiles"] = "true"
            }
            inheritClassPath = true
            kspIncremental = true
            this.kspWithCompilation = kspWithCompilation
        }

        return compilation.compile()
    }
}