package io.mths.kava.processor.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.*
import io.mths.kava.processor.generator.ksp.createScopeBuilder

class KavaVisitor(
    private val file: FileSpec.Builder
) : KSVisitorVoid() {
    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration,
        data: Unit
    ) = with(classDeclaration) {
        file.addFunctions(
            createScopeBuilder(),
/*
            toSnowflake(),
            toSnowflakeLambda(),

            toDelegation(),
            toDelegationLambda(), */
        )
    }
}

internal fun FileSpec.Builder.addFunctions(vararg functions: FunSpec) {
    for (function in functions) {
        addFunction(function)
    }
}