package io.mths.kava.processor.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.*
import io.mths.kava.GenerateExtensions
import io.mths.kava.processor.generator.extensions.*
import io.mths.kava.processor.generator.ksp.annotation.getAnnotation
import io.mths.kava.processor.generator.extensions.info.ExtensionInfo
import io.mths.kava.processor.generator.extensions.info.of
import io.mths.kava.processor.generator.poet.*

class KavaVisitor(
    private val file: FileSpec.Builder
) : KSVisitorVoid() {
    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration,
        data: Unit
    ) = with(ExtensionInfo.of(classDeclaration)) {
        val annotation = classDeclaration.getAnnotation<GenerateExtensions>()

        file.addProperties(
            snowflake(),
            snowflakeLambda()
        )

        file.addFunctions(
            delegation(),
            delegationLambda(),
            scopeBuilder(annotation.scopeName)
        )
    }
}