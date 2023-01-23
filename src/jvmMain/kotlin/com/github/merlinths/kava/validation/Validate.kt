package com.github.merlinths.kava.validation

import com.github.merlinths.kava.ValidScope
import com.github.merlinths.kava.validator.UnitValidator
import com.github.merlinths.kava.validator.Validator
import java.util.*

/**
 * Provides a scope to deal with validation logic.
 * Everytime a possible invalid value like an empty [Optional] or null is accessed,
 * the scope immediately skips the remaining code inside and returns an invalid result.
 */
fun <Type, WrapperType> Validator<Type, WrapperType>.validate(
    block: ValidScope<Type>.() -> Type
): WrapperType {
    var result: WrapperType = invalid

    launchValidation(block) { validResult ->
        result = validResult
    }

    return result
}

/**
 * Special type of validation scope with return type [Unit].
 */
fun valid(
    block: ValidScope<Unit>.() -> Unit
) = UnitValidator()
    .validate(block)