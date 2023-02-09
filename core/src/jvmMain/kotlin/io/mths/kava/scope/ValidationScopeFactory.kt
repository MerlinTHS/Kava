package io.mths.kava.scope

import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.scope.throwing.ThrowingValidationScope

object ValidationScopeFactory {
    fun <Type> createScope() = ThrowingValidationScope<Type>()
}

fun <Type, WrapperType> validate(
    validator: Validator<Type, WrapperType>,
    block: ValidationScope<Type>.() -> Type
) = ValidationScopeFactory.createScope<Type>()
    .host(validator, block)