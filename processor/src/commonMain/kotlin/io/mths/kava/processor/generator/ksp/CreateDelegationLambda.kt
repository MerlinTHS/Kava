package io.mths.kava.processor.generator.ksp

import io.mths.kava.processor.generator.poet.delegationLambda
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FunSpec

fun KSClassDeclaration.createDelegationLambda(): FunSpec {
    return delegationLambda()
}