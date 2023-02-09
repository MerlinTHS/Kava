package io.mths.kava.processor.util

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.symbol.KSClassDeclaration
import kotlin.reflect.KClass

fun KSClassDeclaration.hasSupertype(supertype: KClass<*>): Boolean =
    getAllSuperTypes().any { type ->
        getQualifiedName(supertype) == type.declaration.qualifiedName?.asString()
    }