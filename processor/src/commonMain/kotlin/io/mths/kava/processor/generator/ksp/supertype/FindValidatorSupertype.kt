package io.mths.kava.processor.generator.ksp.supertype

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import io.mths.kava.Validator

fun KSClassDeclaration.findValidatorSupertype(): KSType =
    getAllSuperTypes()
        .find(KSType::isValidator)
        ?: throw NoValidatorSupertype(simpleName.asString())

internal fun KSType.isValidator(): Boolean =
    isClass(Validator::class)