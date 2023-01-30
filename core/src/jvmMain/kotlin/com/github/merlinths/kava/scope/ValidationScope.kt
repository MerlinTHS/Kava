package com.github.merlinths.kava.scope

import com.github.merlinths.kava.validator.Validator

/**
 * Provides a scope to deal with validation logic.
 * Everytime a possible invalid value like an empty Optional or null is accessed,
 * the scope immediately skips the remaining code inside and returns an invalid result.
 */
interface ValidationScope<ScopeType> {
    /**
     * This method stops execution of the current [ValidationScope].
     *
     * The Scope returns an invalid result ( like null or an empty [Optional] ).
     *
     * @return The return type only exists to make the compiler happy. The
     * execution is stopped before anything gets returned.
     */
    fun <Type> fail(): Type

    /**
     * Runs [block] in the current scope and uses validator
     * to return a wrapped result.
     */
    fun <WrapperType> host(
        validator: Validator<ScopeType, WrapperType>,
        block: ValidationScope<ScopeType>.() -> ScopeType
    ): WrapperType
}


fun <Type, WrapperType> validate(
    validator: Validator<Type, WrapperType>,
    block: ValidationScope<Type>.() -> Type
) = ValidationScopeFactory
    .createScope<Type>()
    .host(validator, block)