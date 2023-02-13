package io.mths.kava.processor.generator.ksp.supertype

import io.mths.kava.GenerateExtensions
import io.mths.kava.Validator

class NoValidatorSupertype(
    className: String
) : Exception(
    "[$className] Missing super type! " +
    "Symbols annotated with ${GenerateExtensions::class.qualifiedName} had to extend ${Validator::class.qualifiedName}!"
)