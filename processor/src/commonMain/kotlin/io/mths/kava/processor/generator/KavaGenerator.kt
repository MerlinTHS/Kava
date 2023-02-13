package io.mths.kava.processor.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo

class KavaGenerator(
    private val declarations: List<KSClassDeclaration>,
    private val codeGenerator: CodeGenerator,
    private val dependencies: Dependencies
) {
    fun generateSeparateFiles() {
        for (declaration in declarations) {
            val file = declaration.createExtensionFile()
            declaration generateExtensionsTo file

            generate(file)
        }
    }

    fun generateSingleFile() {
        val file = FileSpec.builder("io.mths.kava.extensions", "GeneratedKavaValidators" + hashCode().toString())

        for (declaration in declarations) {
            declaration generateExtensionsTo file
        }

        generate(file)
    }

    private fun generate(file: FileSpec.Builder) {
        file.build()
            .writeTo(codeGenerator, dependencies)
    }

    private infix fun KSClassDeclaration.generateExtensionsTo(file: FileSpec.Builder) {
        accept(
            KavaVisitor(file),
            Unit
        )
    }

    private fun KSClassDeclaration.createExtensionFile() =
        FileSpec.builder(
            packageName = (packageName.asString() + ".extensions"),
            fileName = simpleName.asString() + "Extensions" + simpleName.hashCode().toString()
        )
}