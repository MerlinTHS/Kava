package io.mths.kava.processor

import io.mths.kava.processor.generator.KavaGenerator
import io.mths.kava.processor.sequence.ifNotEmpty
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import io.mths.kava.GenerateExtensions
import io.mths.kava.Validator
import io.mths.kava.processor.generator.aggregatingDependencies
import io.mths.kava.processor.options.KavaOptions
import io.mths.kava.processor.util.getQualifiedName
import io.mths.kava.processor.util.hasSupertype

class KavaProcessor(
    private val codeGenerator: CodeGenerator,
    private val options: KavaOptions,
    private val LOG: KSPLogger
) : SymbolProcessor {
    private val alreadyHandled =
        mutableSetOf<KSClassDeclaration>()

    override fun process(
        resolver: Resolver
    ): List<KSAnnotated> = with(resolver) {
        getKavaSymbols()
            .ifNotEmpty {
                generateExtensions(aggregatingDependencies)
            }.filterUnprocessed()
    }

    private fun List<KSClassDeclaration>.generateExtensions(
        dependencies: Dependencies
    ) = buildGenerator(dependencies).run {
        if (options.separateFiles) {
            generateSeparateFiles()
        } else {
            generateSingleFile()
        }

        alreadyHandled += this@generateExtensions
    }

    private fun List<KSClassDeclaration>.buildGenerator(
        dependencies: Dependencies
    ) = KavaGenerator(declarations = this, codeGenerator, dependencies)

    private fun Resolver.getKavaSymbols(): List<KSClassDeclaration> {
        val qualifiedName = getQualifiedName(GenerateExtensions::class)

        return getSymbolsWithAnnotation(qualifiedName)
            .filterIsInstance<KSClassDeclaration>()
            .onEach(::checkSupertypes)
            .filter(::isAlreadyHandled)
            .toList()
    }

    private fun isAlreadyHandled(declaration: KSClassDeclaration) =
        declaration.containingFile?.filePath !in alreadyHandled.mapNotNull { it.containingFile?.filePath }

    private fun checkSupertypes(declaration: KSClassDeclaration) {
        if (!declaration.hasSupertype(Validator::class)) {
            LOG.error("Classes annotated with [GenerateExtensions] had to have [Validator] as a supertype!")
        }
    }

    private fun <Type : KSAnnotated> List<Type>.filterUnprocessed() =
        filterNot(KSAnnotated::validate)
            .toList()
}