package com.github.merlinths.kava.scope

import com.github.merlinths.kava.ValidationScope
import com.github.merlinths.kava.Validator
import com.github.merlinths.kava.scope.throwing.ThrowingValidationScope

object ValidationScopeFactory {
    fun <Type> createScope() = ThrowingValidationScope<Type>()
}

fun <Type, WrapperType> validate(
    validator: Validator<Type, WrapperType>,
    block: ValidationScope<Type>.() -> Type
) = ValidationScopeFactory
    .createScope<Type>()
    .host(validator, block)