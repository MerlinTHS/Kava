package com.github.merlinths.kava.scope.throwing

import com.github.merlinths.kava.ValidationScope
import com.github.merlinths.kava.Validator

/**
 * A validation scope that skips remaining code by throwing a [ValidationException].
 *
 *  Note: **Be careful when catching exceptions too generally!**
 */
class ThrowingValidationScope<ScopeType> : ValidationScope<ScopeType> {
    override fun <Type> fail() =
        throw ValidationException

    override fun <WrapperType> host(
        validator: Validator<ScopeType, WrapperType>,
        block: ValidationScope<ScopeType>.() -> ScopeType
    ) = with(validator) {
        try {
            valid(value = block())
        } catch (exception: ValidationException) {
            invalid
        }
    }
}