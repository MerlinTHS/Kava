package com.github.merlinths.kava.scope

import com.github.merlinths.kava.scope.throwing.ThrowingValidationScope

object ValidationScopeFactory {
    fun <Type> createScope() = ThrowingValidationScope<Type>()
}